package com.kodnest.tradeNest.Controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodnest.tradeNest.Entitys.Productss;
import com.kodnest.tradeNest.serviceContract.AdminPorductServiceContract;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/admin/product")
@CrossOrigin(origins = "https://tradnest-frontend.vercel.app", allowCredentials = "true")
public class AdminProductController {

	
	private AdminPorductServiceContract adminPorductServiceContract;

	public AdminProductController(AdminPorductServiceContract adminPorductServiceContract) {
		super();
		this.adminPorductServiceContract = adminPorductServiceContract;
	}
	
	@PostMapping("/add/products")
	public ResponseEntity<?> addProducts(@RequestBody Map<String, Object> req, HttpServletRequest request) {
		try {
			String name = (String) req.get("name");
			String  description = (String) req.get("description");
			Double price = Double.parseDouble(req.get("price").toString());
			Integer categoryId = (Integer) req.get("categoryId");
			String imageUrl = (String) req.get("imageUrl");
			Integer stock = (Integer) req.get("stock");
			
			Productss product =  adminPorductServiceContract.saveTheProducts(name, description, price, categoryId, imageUrl, stock); 
			return ResponseEntity.ok(product);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("messgae","Some thing went wrong"));
		}
	}
	@DeleteMapping("/delete/products")
	public ResponseEntity<?> deleteProduct(@RequestBody Map<String, Object> req, HttpServletRequest request) {
		try {
			Integer productId = (Integer) req.get("productId");
			
			  adminPorductServiceContract.deletProduct(productId);  
			return ResponseEntity.ok(Map.of("Message", "Product deleted successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("messgae","Some thing went wrong"));
		}
	}
}
