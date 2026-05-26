package com.kodnest.tradeNest.Controller;

import java.util.HashMap;
import java.util.Map; 

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodnest.tradeNest.Entitys.User;
import com.kodnest.tradeNest.serviceContract.JWTAuthServiceContract;
import com.kodnest.tradeNest.serviceContract.userServiceContract;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")

public class UserController {
		
	private userServiceContract userServiceContract;
	private JWTAuthServiceContract jwtAuthServiceContract;
	
	public UserController(userServiceContract userServiceContract,JWTAuthServiceContract jwtAuthServiceContract) {
		super();
		this.userServiceContract = userServiceContract;
		this.jwtAuthServiceContract = jwtAuthServiceContract;
	}


	@PostMapping("/regestration")
	public ResponseEntity<?> regestration(@RequestBody User user) {
		System.out.println(user);
		try {
			User SavedUser = userServiceContract.regestraction(user);
			System.out.println(SavedUser + "---->");
			return ResponseEntity.ok().body(Map.of("messgae","User regestered successfully"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("messgae","User regestere failed"));
		}
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
		try {
			System.out.println(user + "-----> user in controller");
			User verifiedUser = userServiceContract.userAuthendication(user.getUsername(), user.getPassword());
			System.out.println(verifiedUser + "-----> user in controller");
			String token = jwtAuthServiceContract.generateToken(verifiedUser);
			
			ResponseCookie cookie = ResponseCookie.from("AuthToken", token)
					.httpOnly(true)
					 .secure(false)
					 .path("/")
					 .maxAge(60 * 60)
					 .sameSite("Lax")
					 .build(); 
			
					response.addHeader("Set-Cookie", cookie.toString());
					System.out.println("line -----> 64");
					return ResponseEntity.ok(Map.of("Message", "Login successfull"
							,"role",verifiedUser.getRole().name(),
							"username", verifiedUser.getUsername()));
		}catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("Messgae" , "Login failde"));
		}
		
	}
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			User user = (User) request.getAttribute("AuthendicatedUser");
			jwtAuthServiceContract.logoutUser(user);
			ResponseCookie cookie = ResponseCookie.from("AuthToken", null)
					.httpOnly(true)
					 .secure(false)
					 .path("/")
					 .maxAge(0)
					 .build(); 
			response.addHeader("Set-Cookie", cookie.toString());
			return ResponseEntity.ok(Map.of("message", "Logout successfull"));
		} catch (Exception e) {
			Map<String, String> errorResponce = new HashMap<>();
			errorResponce.put("message", "Logout failed");
			return ResponseEntity.status(500).body(errorResponce);
		}
		
	}
}
