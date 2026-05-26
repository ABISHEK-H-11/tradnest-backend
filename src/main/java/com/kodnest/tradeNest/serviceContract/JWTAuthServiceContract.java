package com.kodnest.tradeNest.serviceContract;

import com.kodnest.tradeNest.Entitys.User;

public interface JWTAuthServiceContract {
	 public String generateToken(User user);
	 public String gendrateNewToken(User user);
	 public boolean validateToken(String token);
	 public String extractUsername(String token);
	 public void logoutUser(User user);
}
