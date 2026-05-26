package com.kodnest.tradeNest.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kodnest.tradeNest.Entitys.OrderItem;
import com.kodnest.tradeNest.Entitys.Productimages;
import com.kodnest.tradeNest.Entitys.User;
import com.kodnest.tradeNest.Repository.OrderRepository;
import com.kodnest.tradeNest.Repository.ProductImageRepository;
import com.kodnest.tradeNest.serviceContract.OrderServiceContract;
@Service
public class OrderService implements OrderServiceContract{

	private OrderRepository orderRepository;
	
	private ProductImageRepository productImageRepository;
	
	
	public OrderService(OrderRepository orderRepository, ProductImageRepository productImageRepository) {
		super();
		this.orderRepository = orderRepository;
		this.productImageRepository = productImageRepository;
	}


	@Override
	public Map<String, Object> getOrderDetails(User user) {
		List<OrderItem> orderitems = orderRepository.findByUserId(user.getUserId());
		
		Map<String, Object> responce = new HashMap<>();
		responce.put("username", user.getUsername());
		responce.put("role", user.getRole().name());
		
		List<Map<String, Object>> products = new ArrayList<>();
		for(OrderItem item : orderitems) {
			Map<String, Object> product = new HashMap<>();
			List<Productimages> productimages = productImageRepository.findByProductss_ProductId(item.getProductss().getProductId());
			List<String> img = new ArrayList<>();
			for(Productimages productimages2 : productimages) {
				img.add(productimages2.getImageUrl());
			}
			product.put("order_id", item.getOrder().getOrderId());
			product.put("quantity", item.getQuantity());
			product.put("total_price", item.getTotalPrice());
			product.put("product_id", item.getProductss().getProductId());
			product.put("name", item.getProductss().getName());
			product.put("description", item.getProductss().getDescription());
			product.put("price_per_unit", item.getProductss().getPrice());
			product.put("image_url", img);
			products.add(product);
		}
		Map<String, Object> orders = new HashMap<>();
		orders.put("products", products);
		responce.put("orders",orders);
		return responce;
	}

}
