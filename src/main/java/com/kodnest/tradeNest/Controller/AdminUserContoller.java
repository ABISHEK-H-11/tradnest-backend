package com.kodnest.tradeNest.Controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodnest.tradeNest.Entitys.User;
import com.kodnest.tradeNest.serviceContract.AdminUserServiceContract;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/admin/user")
@CrossOrigin(origins = "https://tradnest-frontend.vercel.app", allowCredentials = "true")
public class AdminUserContoller {
	
	private AdminUserServiceContract adminUserServiceContract;

	public AdminUserContoller(AdminUserServiceContract adminUserServiceContract) {
		super();
		this.adminUserServiceContract = adminUserServiceContract;
	}
	
	@PostMapping("/modify")
	public ResponseEntity<?> modifyUser(@RequestBody Map<String, Object> req) {
		try {
			Integer userId = (Integer) req.get("userId");
			String username =  (String) req.get("username");
			String email = (String) req.get("email");
			String role = (String) req.get("role");
			
			User user =  adminUserServiceContract.modifyUser(userId, username, email, role);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message","Some thing went wrong"));
		}
	}
	
	@PostMapping("/getbyid")
	public ResponseEntity<?> getbyId(@RequestBody Map<String , Object> req) {
		try {
			Integer user = Integer.parseInt(req.get("userId").toString());
			User ValidUser = adminUserServiceContract.getbyUserId(user);
			return ResponseEntity.ok(ValidUser);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message","Some thing went wrong"));
		}
	}
	

}
