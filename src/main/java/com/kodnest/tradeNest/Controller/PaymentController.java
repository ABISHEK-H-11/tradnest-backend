package com.kodnest.tradeNest.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodnest.tradeNest.Entitys.User;
import com.kodnest.tradeNest.Repository.productsRepository;
import com.kodnest.tradeNest.serviceContract.PaymentServiceContract;
import com.razorpay.RazorpayException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/customer/payment")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class PaymentController {

	private PaymentServiceContract serviceContract;


	public PaymentController(PaymentServiceContract serviceContract) {
		super();
		this.serviceContract = serviceContract;
	}

	@PostMapping("/create")
	public ResponseEntity<?> creatOrderId(@RequestBody Map<String, Object> req, HttpServletRequest request) {
			
			try {
				User user = (User) request.getAttribute("AuthendicatedUser");
				Number amountNumber = (Number) req.get("totalAmount");
		        BigDecimal totalAmount =
		                BigDecimal.valueOf(amountNumber.doubleValue());
				String id = serviceContract.createOrder(user, totalAmount);
				return ResponseEntity.ok(Map.of("orderId",id));
			} catch (RazorpayException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating Razorpay order: " + e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invaldi Request data: " + e.getMessage());
			}
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> verify(@RequestBody Map<String, Object> req, HttpServletRequest request) {
		User user = (User) request.getAttribute("AuthendicatedUser");
		try {
			String razorpayOrderId = (String) req.get("razorpayOrderId");
			String razorpayPaymentId = (String) req.get("razorpayPaymentId");
			String razorpaySignature = (String) req.get("razorpaySignature");
			
			boolean isVerified = serviceContract.verifyPayment(razorpayOrderId, razorpayPaymentId, razorpaySignature, user);
			if(isVerified) {
				return ResponseEntity.ok("Payment Verified Successfull");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment Verifictaion filed");
			}
		} catch (RazorpayException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating Razorpay order: " + e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invaldi Request data: " + e.getMessage());
		}
		
	}
}
