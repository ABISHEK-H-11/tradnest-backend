package com.kodnest.tradeNest.serviceContract;

import com.kodnest.tradeNest.Entitys.User;

public interface userServiceContract {
	public User regestraction(User user);
	public User userAuthendication(String username, String password);
}
