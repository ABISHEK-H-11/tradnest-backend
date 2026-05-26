package com.kodnest.tradeNest.serviceContract;

import com.kodnest.tradeNest.Entitys.User;

public interface AdminUserServiceContract {

	public User modifyUser(Integer userId, String username, String email, String role);
	public User getbyUserId(Integer userId);
}
