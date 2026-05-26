package com.kodnest.tradeNest.Services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kodnest.tradeNest.Entitys.Role;
import com.kodnest.tradeNest.Entitys.User;
import com.kodnest.tradeNest.Repository.UserRepository;
import com.kodnest.tradeNest.serviceContract.AdminUserServiceContract;

@Service
public class AdminUserService implements AdminUserServiceContract{
	
	private UserRepository userRepository;
	
	
	public AdminUserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}




	@Override
	public User modifyUser(Integer userId, String username, String email, String role) {
		Optional<User> user = userRepository.findById(userId);
		User validUser = null;
		if(user.isPresent()) {
			 validUser = user.get();
			 if(username != null && !username.isEmpty() && validUser != null) {
					validUser.setUsername(username);
				}
				if(email != null && !email.isEmpty() && validUser != null) {
					validUser.setEmail(email);
				}
				if(role != null && !role.isEmpty() && validUser != null) {
					validUser.setRole(Role.valueOf(role));
				}
				validUser.setUpdated_at(LocalDateTime.now());
				return userRepository.save(validUser);
		}
		
		return null;
		
	}

	

	@Override
	public User getbyUserId(Integer userId) {
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			User vaildUser = user.get();
			return vaildUser;
		}
		return null;
	}







	

}
