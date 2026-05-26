package com.kodnest.tradeNest.Services;

import java.nio.charset.StandardCharsets; 
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kodnest.tradeNest.Entitys.Jwt_tokens;
import com.kodnest.tradeNest.Entitys.User;
import com.kodnest.tradeNest.Repository.JwtRepository;
import com.kodnest.tradeNest.Repository.UserRepository;
import com.kodnest.tradeNest.serviceContract.JWTAuthServiceContract;
import com.kodnest.tradeNest.serviceContract.userServiceContract;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class UserServices implements userServiceContract, JWTAuthServiceContract{
	
	private BCryptPasswordEncoder passwordEncoder;
	private UserRepository repository;
	private JwtRepository jwtRepository;
	private final Key SIGNING_KEY;
	
	
	public UserServices(BCryptPasswordEncoder passwordEncoder, UserRepository repository
			,JwtRepository jwtRepository, @Value("${jwt.secret}") String jwtsecret) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.repository = repository;
		this.jwtRepository = jwtRepository;
		
		if(jwtsecret.getBytes(StandardCharsets.UTF_8).length < 64) {
			throw new RuntimeException("JWT_SECRET in appliocation.properties"
					+ " must be at leat 64 bytes long for HS512.");
		}
		this.SIGNING_KEY = Keys.hmacShaKeyFor(jwtsecret.getBytes(StandardCharsets.UTF_8)) ;
	}


	@Override
	public User regestraction(User user) {
		System.out.println(user + "----<");
		User user1 =  repository.findByUsername(user.getUsername());
		User user2 = repository.findByEmail(user.getEmail());
		if(user1 != null ) {
			throw new RuntimeException("User wthi user name is alredy exist");
		}
		if(user2 != null) {
			throw new RuntimeException("User with email is alredy exist");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCreated_at(LocalDateTime.now());
		user.setUpdated_at(LocalDateTime.now());
		System.out.println(user + "---->");
		return repository.save(user);
	}


	@Override
	public  User userAuthendication(String username, String password){
		User existingUser = repository.findByUsername(username);
		if(existingUser != null) {
			if(passwordEncoder.matches(password, existingUser.getPassword())) {
				
				return existingUser;
			}else {
				throw new RuntimeException("Invaild user or passowrd");
			}
		} else {
			throw new RuntimeException("Invaild user or passowrd");
		}
		
		
	}


	@Override
	public String generateToken(User user) {
		String token;
		LocalDateTime now = LocalDateTime.now();
		Jwt_tokens jwt_tokens = jwtRepository.findByUserId(user.getUserId());
		System.out.println(jwt_tokens);
		if(jwt_tokens != null && now.isBefore(jwt_tokens.getExpires_at())) {
			token =  jwt_tokens.getToken();
		}else {
			token = gendrateNewToken(user);
			if(jwt_tokens != null) {
				jwtRepository.delete(jwt_tokens);
			}
			saveToken(user, token);
		}
		return token;
	}

	@Override
	public String gendrateNewToken(User user) {
		return Jwts.builder()
				.setSubject(user.getUsername())
				.claim("role", user.getRole().name())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 3600000))
				.signWith(SIGNING_KEY)
				.compact();
	
	}
	
	public void saveToken(User user, String token) {
		jwtRepository.save(new Jwt_tokens(user, token, LocalDateTime.now().plusHours(1)));
	}


	@Override
	public boolean validateToken(String token) {
		Jwt_tokens jwt_tokens = jwtRepository.findByToken(token);
		LocalDateTime now = LocalDateTime.now();
		if(jwt_tokens != null) {
			if(now.isBefore(jwt_tokens.getExpires_at())) {
				return true;
			}
		}
		return false;
	}


	@Override
	public String extractUsername(String token) {
		Claims claims = Jwts
				.parser()
				.setSigningKey(SIGNING_KEY)
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}


	@Override
	public void logoutUser(User user) {
		// TODO Auto-generated method stub
		Jwt_tokens jwt_tokens=  jwtRepository.findByUserId(user.getUserId());
		if(jwt_tokens != null) {
			jwtRepository.delete(jwt_tokens);
		}
	}


	


}
