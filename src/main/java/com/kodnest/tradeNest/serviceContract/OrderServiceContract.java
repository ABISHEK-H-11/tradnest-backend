package com.kodnest.tradeNest.serviceContract;

import java.util.Map;

import com.kodnest.tradeNest.Entitys.User;

public interface OrderServiceContract {
	public Map<String, Object> getOrderDetails(User user);
}
