package com.kodnest.tradeNest.Controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodnest.tradeNest.Entitys.User;
import com.kodnest.tradeNest.serviceContract.OrderServiceContract;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/customer/order")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class OrderContoller {
	
	private OrderServiceContract orderServiceContract;

	public OrderContoller(OrderServiceContract orderServiceContract) {
		super();
		this.orderServiceContract = orderServiceContract;
	}
	
	@GetMapping("/details")
	public ResponseEntity<?> getOrderDetails(HttpServletRequest request) {
		try {
			User user = (User) request.getAttribute("AuthendicatedUser");
			Map<String, Object> responce = orderServiceContract.getOrderDetails(user);
			return ResponseEntity.ok(responce);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message", "not good"));
		}
	}
	
}
