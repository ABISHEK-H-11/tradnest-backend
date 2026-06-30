package com.kodnest.tradeNest.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodnest.tradeNest.Entitys.Productimages;
import com.kodnest.tradeNest.Entitys.Productss;
import com.kodnest.tradeNest.Entitys.User;
import com.kodnest.tradeNest.serviceContract.ProductServiceContract;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/customer/products")
@CrossOrigin(origins = "https://tradnest-frontend.vercel.app", allowCredentials = "true")
public class ProductContoller {
	
	private ProductServiceContract productServiceContract;

	public ProductContoller(ProductServiceContract productServiceContract) {
		super();
		this.productServiceContract = productServiceContract;
	}
	
	@GetMapping()
	public ResponseEntity<?> getProduct(@RequestParam String category, HttpServletRequest httpServletRequest) {
		try {
			User authorizeUser = (User) httpServletRequest.getAttribute("AuthendicatedUser");
			
			if(authorizeUser == null) {
				return ResponseEntity.status(401).body(Map.of("error", "Unauthorized Acces"));
			}
			
			List<Productss> productsses = productServiceContract.getProductsByCatogrey(category);
			Map<String, Object> responce = new HashMap<>();
			responce.put("User",Map.of("role",authorizeUser.getRole().name(),"name", authorizeUser.getUsername()));
			List<Map<String, Object>> products = new ArrayList<>();
			for(Productss productss : productsses) {
				List<String> productimagesds = productServiceContract.getProductsProductsImage(productss);
				Map<String, Object> product = new HashMap<>();
				product.put("images", productimagesds);
				product.put("price", productss.getPrice());
				product.put("name", productss.getName());
				product.put("prodct_id", productss.getProductId());
				product.put("description", productss.getDescription());
				product.put("stock", productss.getStock());
				products.add(product);
				
			
			}
			responce.put("products", products);
			return ResponseEntity.ok(responce);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}
	

}
