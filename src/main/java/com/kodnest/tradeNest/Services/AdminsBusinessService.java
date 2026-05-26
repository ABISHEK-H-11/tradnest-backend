package com.kodnest.tradeNest.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kodnest.tradeNest.Entitys.Order;
import com.kodnest.tradeNest.Entitys.OrderItem;
import com.kodnest.tradeNest.Repository.OrderItemRepository;
import com.kodnest.tradeNest.Repository.OrderRepository;
import com.kodnest.tradeNest.serviceContract.AdminBusnessServiceContract;

@Service
public class AdminsBusinessService implements AdminBusnessServiceContract{
	
	private OrderItemRepository orderItemRepository;
	private OrderRepository orderRepository;

	public AdminsBusinessService(OrderItemRepository orderItemRepository, OrderRepository orderRepository) {
		super();
		this.orderItemRepository = orderItemRepository;
		this.orderRepository = orderRepository;
	}

	@Override
	public Map<String, Object> getMonthlyBusiness(int month, int year) {
		List<Order> orders = orderRepository.findMonthlyBusiness(month, year);
		
		return CreatingResponce(orders);
	}
	@Override
	public Map<String, Object> getDailyBusiness(String data) {
		LocalDate date = LocalDate.parse(data);
		List<Order> orders = orderRepository.findDailyBusiness(date);
		return CreatingResponce(orders);
	}
	
	@Override
	public Map<String, Object> getYearlyBusiness(int year) {
		List<Order> orders =  orderRepository.findYearlyBusiness(year);
		return CreatingResponce(orders);
	}
	
	@Override
	public Map<String, Object> getOverAllBusiness() {
		List<Order> orders =  orderRepository.findByStatusSuccess();
		return CreatingResponce(orders);
	}
	
	public Map<String, Object> CreatingResponce(List<Order> orders) {
//		totalrevanue, catogri = quantity
		double totalrevinue = 0.0;
		Map<String, Object> responec = new HashMap<>();
		Map<String, Integer> catagory  = new HashMap<>();
		for(Order order : orders) {
			totalrevinue += order.getTotalAmount().doubleValue();
			List<OrderItem> orderItems =  orderItemRepository.findByOrder_OrderId(order.getOrderId());
			for(OrderItem item : orderItems) {
				String catagoryName = item.getProductss().getCategories().getCategoryName();
				catagory.put(catagoryName, catagory.getOrDefault(catagoryName, 0) + item.getQuantity());
			}
		}
		responec.put("totalRevinue", totalrevinue);
		responec.put("catagory",catagory);
		return responec;
	}

	

	
	
}
