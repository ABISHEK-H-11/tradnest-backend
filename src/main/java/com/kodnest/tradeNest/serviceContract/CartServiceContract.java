package com.kodnest.tradeNest.serviceContract;

import java.util.Map;

import com.kodnest.tradeNest.Entitys.User;

public interface CartServiceContract {
	public void addToCart(User user, Integer productId, int queantity);
	public void deleteCartItem(User user, Integer productId);
	public void updateCartItem(User user, Integer productId, int queantity);
	public Map<String, Object> getCartDetails(User user);
	public int findCartCout(User user);
}
