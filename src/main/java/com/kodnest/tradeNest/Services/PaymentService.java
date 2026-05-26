package com.kodnest.tradeNest.Services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kodnest.tradeNest.Entitys.Cart_items;
import com.kodnest.tradeNest.Entitys.OrderItem;
import com.kodnest.tradeNest.Entitys.Status;
import com.kodnest.tradeNest.Entitys.User;
import com.kodnest.tradeNest.Repository.CartRepository;
import com.kodnest.tradeNest.Repository.OrderItemRepository;
import com.kodnest.tradeNest.Repository.OrderRepository;
import com.kodnest.tradeNest.serviceContract.PaymentServiceContract;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.transaction.Transactional;

@Service
public class PaymentService implements PaymentServiceContract{
	@Value("${Razorpay.key_id}")
	private String razorpayKeyId;
	
	@Value("${Razorpay.key_secret}")
	private String razorpayKeySecret;
	
	private OrderRepository orderRepository;
	
	private CartRepository cartRepository;
	
	private OrderItemRepository orderItemRepository;
	
	
	
	public PaymentService(OrderRepository orderRepository, CartRepository cartRepository,
			OrderItemRepository orderItemRepository) {
		super();
		this.orderRepository = orderRepository;
		this.cartRepository = cartRepository;
		this.orderItemRepository = orderItemRepository;
	}

	@Override
	@Transactional
	public String createOrder(User user, BigDecimal totalAmount) throws RazorpayException {
		RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId,razorpayKeySecret);
		JSONObject responce = new JSONObject();
		responce.put("amount", totalAmount.multiply(BigDecimal.valueOf(100)).intValue());
		responce.put("currency", "INR");
		responce.put("receipt", "txt_"+ System.currentTimeMillis());
		
		Order rayzorPayOrder = razorpayClient.orders.create(responce);
		
		com.kodnest.tradeNest.Entitys.Order order = new com.kodnest.tradeNest.Entitys.Order();
		order.setOrderId(rayzorPayOrder.get("id"));
		order.setStatus(Status.PENDING);
		order.setTotalAmount(totalAmount);
		order.setCreated_at(LocalDateTime.now());
		order.setUpdated_at(LocalDateTime.now());
		order.setUser(user);
		orderRepository.save(order);
		return order.getOrderId();
	}

	@Override
	@Transactional
	public boolean verifyPayment(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature, User user) throws RazorpayException {
		JSONObject responce = new JSONObject();
		responce.put("razorpay_order_id", razorpayOrderId);
		responce.put("razorpay_payment_id", razorpayPaymentId);
		responce.put("razorpay_signature", razorpaySignature);
		
		boolean isVerified =  Utils.verifyPaymentSignature(responce, razorpayKeySecret);
		if(isVerified) {
			com.kodnest.tradeNest.Entitys.Order order = orderRepository.findById(razorpayOrderId).orElseThrow(()-> new RuntimeException("order is not found"));
			order.setStatus(Status.SUCCESS);
			order.setUpdated_at(LocalDateTime.now());
			orderRepository.save(order);
			
			List<Cart_items> cart_items = cartRepository.findByUserId(user.getUserId());
			for(Cart_items items : cart_items) {
				OrderItem item = new OrderItem();
				item.setOrder(order);
				item.setPricePerUnit(items.getProducts().getPrice());
				item.setProductss(items.getProducts());
				item.setQuantity(items.getQuantity());
				item.setTotalPrice(items.getProducts().getPrice().multiply(BigDecimal.valueOf(items.getQuantity())));
				orderItemRepository.save(item);
			}
			cartRepository.deletCartUsingUserId(user.getUserId());
			return true;
		}
		
		return false;
	}


	


	

}
