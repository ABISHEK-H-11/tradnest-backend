package com.kodnest.tradeNest.Controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodnest.tradeNest.Entitys.User;
import com.kodnest.tradeNest.Services.CartService;
import com.kodnest.tradeNest.serviceContract.CartServiceContract;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/customer/cart")
@CrossOrigin(origins = "https://tradnest-frontend.vercel.app", allowCredentials = "true")
public class CartItemController {

	private CartServiceContract cartServiceContract;
	
	public CartItemController(CartServiceContract cartServiceContract ) {
		
		this.cartServiceContract = cartServiceContract;
	
	}

	@PostMapping("/add")
	public ResponseEntity<?> addItems(@RequestBody Map<String, Object> req, HttpServletRequest request) {
	   System.out.println("controll");
		int productId = (int) req.get("productId");
	    System.out.println(productId + "----> productId");
		int quantity = req.containsKey("quantity") ? (int) req.get("quantity") : 1;
		User user = (User) request.getAttribute("AuthendicatedUser");
		cartServiceContract.addToCart(user, productId, quantity);
		return ResponseEntity.ok().body(Map.of("Messgae","Item add successfully"));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteItem(@RequestBody Map<String, Object> req, HttpServletRequest request) {
		System.out.println("asdklsk[");
		User user = (User) request.getAttribute("AuthendicatedUser");
		Integer productId = (Integer)req.get("productId");
		
		cartServiceContract.deleteCartItem(user, productId);
		return ResponseEntity.ok().body(Map.of("Messgae","Item deleted successfully"));
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateCartItem(@RequestBody Map<String, Object> req, HttpServletRequest request) {
		User user = (User) request.getAttribute("AuthendicatedUser");
		Integer productId = (Integer) req.get("productId");
		int quantity = (int) req.get("quantity");
		cartServiceContract.updateCartItem(user, productId, quantity);
		return ResponseEntity.ok().body(Map.of("Messgae","Item updated successfully"));
	}
	
	@GetMapping("/items")
	public ResponseEntity<?> cartItems(HttpServletRequest request) {
		User user = (User) request.getAttribute("AuthendicatedUser");
		Map<String, Object> responce =  cartServiceContract.getCartDetails(user);
		return ResponseEntity.ok(responce);
	}
	
	@GetMapping("/count")
	public ResponseEntity<?> cartCount(HttpServletRequest request) {
		User user = (User) request.getAttribute("AuthendicatedUser");
		int cartCount = cartServiceContract.findCartCout(user);
		return ResponseEntity.ok(cartCount);
	}
	
}
