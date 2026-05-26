package com.kodnest.tradeNest.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kodnest.tradeNest.Entitys.Cart_items;
import com.kodnest.tradeNest.Entitys.Productimages;
import com.kodnest.tradeNest.Entitys.Productss;
import com.kodnest.tradeNest.Entitys.User;
import com.kodnest.tradeNest.Repository.CartRepository;
import com.kodnest.tradeNest.Repository.ProductImageRepository;
import com.kodnest.tradeNest.Repository.productsRepository;
import com.kodnest.tradeNest.serviceContract.CartServiceContract;
@Service
public class CartService implements CartServiceContract{

	private productsRepository productsRepository;					
	private CartRepository cartRepository;
	private ProductImageRepository imageRepository;


	public CartService(com.kodnest.tradeNest.Repository.productsRepository productsRepository,
			CartRepository cartRepository, ProductImageRepository imageRepository) {
		super();
		this.productsRepository = productsRepository;
		this.cartRepository = cartRepository;
		this.imageRepository = imageRepository;
	}


	@Override
	public void addToCart(User user, Integer productId, int queantity) {
		Optional<Productss> product = productsRepository.findById(productId);
		if(product.isEmpty()) {
			throw new RuntimeException("product is not found");
		}else {
			Productss products = product.get();
			Cart_items cart_items = cartRepository.findByProductIdAndUserId(products.getProductId(), user.getUserId());
			if(cart_items != null) {
				cart_items.setQuantity(cart_items.getQuantity() + queantity);
				cartRepository.save(cart_items);
			}else {
				cartRepository.save(new Cart_items(user, products, queantity));
			}
		}
		
	}


	@Override
	public void deleteCartItem(User user, Integer productId) {
		Optional<Productss> productss = productsRepository.findById(productId);
		if(!productss.isEmpty()) {
			Productss prod = productss.get();
			Cart_items cart_items = cartRepository.findByProductIdAndUserId(prod.getProductId(), user.getUserId());
			if(cart_items != null) {
				cartRepository.delete(cart_items);
			}
		}
		
	}


	@Override
	public void updateCartItem(User user, Integer productId, int queantity) {
		Optional<Productss> productss = productsRepository.findById(productId);
		if(!productss.isEmpty()) {
			Productss prod = productss.get();
			Cart_items cart_items = cartRepository.findByProductIdAndUserId(prod.getProductId(), user.getUserId());
			if(cart_items != null) {
				if(queantity == 0) {
					deleteCartItem(user, prod.getProductId());
				}else {
					cart_items.setQuantity(queantity);
					cartRepository.save(cart_items);
				}
			}
			
			
		}
	}


	@Override
	public Map<String, Object> getCartDetails(User user) {
		List<Cart_items> cart_items = cartRepository.findByUserId(user.getUserId());
		Map<String, Object> responce = new HashMap<>();
		responce.put("User", Map.of("username",user.getUsername(),"role",user.getRole().name()));
		int totalPrice = 0;
		List<Map<String, Object>> products = new ArrayList<>();
		for(Cart_items items : cart_items) {
			Map<String, Object> productDetails = new HashMap<>();
			Productss produt = items.getProducts();
			Optional<Productss> finalProdects =  productsRepository.findById(produt.getProductId());
			if(!finalProdects.isEmpty()) {
				Productss product = finalProdects.get();
				List<Productimages> img =  imageRepository.findByProductss_ProductId(product.getProductId());
				List<String> imgage = new ArrayList<>();
				for(Productimages i : img) {
					imgage.add(i.getImageUrl());
				}
				productDetails.put("quantity", items.getQuantity());
				productDetails.put("totalPice", items.getQuantity() * product.getPrice().doubleValue());
				productDetails.put("image", imgage);
				productDetails.put("product_id", product.getProductId());
				productDetails.put("name", product.getName());
				productDetails.put("description", product.getDescription());
				productDetails.put("price_per_unit", product.getPrice());
				products.add(productDetails);
				totalPrice +=  items.getQuantity() * product.getPrice().doubleValue();
			}
		}
		Map<String, Object> cart = new HashMap<>();
		cart.put("over_All_total_Price", totalPrice);
		cart.put("products", products);
		responce.put("cart",cart);
		return responce;
	}


	@Override
	public int findCartCout(User user) {
		int count = cartRepository.findCartCount(user.getUserId());
		return count;
	}
	

}
