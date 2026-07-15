	package com.kodnest.tradeNest.Filter;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.kodnest.tradeNest.Entitys.Role;
import com.kodnest.tradeNest.Entitys.User;
import com.kodnest.tradeNest.Repository.UserRepository;
import com.kodnest.tradeNest.serviceContract.JWTAuthServiceContract;
import com.kodnest.tradeNest.serviceContract.userServiceContract;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter implements Filter{
	
	private String ALLOWED_ORIGIN = "https://tradnest-frontend-git-main-abisheka1342003-7995s-projects.vercel.app";
	private static String []  UNAUTHENDICATED_PATH = {
			"/api/user/login",
			"/api/user/regestration"
	};
	private JWTAuthServiceContract jwtAuthServiceContract;
	private UserRepository repository;
	
	
	public AuthenticationFilter(JWTAuthServiceContract jwtAuthServiceContract, UserRepository repository) {
		super();
		this.jwtAuthServiceContract = jwtAuthServiceContract;
		this.repository = repository;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			executeFilterLogic(request,response,chain);
		} catch (Exception e) {
			 e.printStackTrace();
			    ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			    return;
		}
		
	}

	public void executeFilterLogic(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		 HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		 HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		 
		 
		 String requestURI = httpServletRequest.getRequestURI();
		if(httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
				setCORSHeaders(httpServletResponse);
				return;
		}
		 if(Arrays.asList(UNAUTHENDICATED_PATH).contains(requestURI)) {
			 chain.doFilter(request, response);
			 return;
		 }	
		 
		String token = getAuthTokenFromCookie(httpServletRequest);
		if(token == null || !jwtAuthServiceContract.validateToken(token)) {
			sendErrorResponce(httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED, "Unotherizesd: Ivaild or missing token");
			return;
		}
		String username = jwtAuthServiceContract.extractUsername(token);
		User user = repository.findByUsername(username);
		if(user == null) {
			sendErrorResponce(httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED: user not found");
			return;
		}
		if(requestURI.startsWith("/api/customer/") && user.getRole() != Role.CUSTOMER) {
			sendErrorResponce(httpServletResponse, HttpServletResponse.SC_FORBIDDEN, "Forbidden: Customer access required");
			return;
		}
		if(requestURI.startsWith("/api/admin/") && user.getRole() != Role.ADMIN) {
			sendErrorResponce(httpServletResponse, HttpServletResponse.SC_FORBIDDEN, "Forbidden: Admin access required");
			return;
		}
		httpServletRequest.setAttribute("AuthendicatedUser", user);
		chain.doFilter(request,response);
		
	}
	

	private void setCORSHeaders(HttpServletResponse httpServletResponse) {
		httpServletResponse.setHeader("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);	
	}
	public String getAuthTokenFromCookie(HttpServletRequest request) {
	    Cookie[] cookies = request.getCookies();
	    if (cookies == null) {
	        return null;
	    }

	    for (Cookie cookie : cookies) {
	        if ("AuthToken".equals(cookie.getName())) {
	            return cookie.getValue();
	        }
	    }

	    return null;
		
	}
	private void sendErrorResponce(HttpServletResponse httpServletResponse, 
			int scForbidden, String string) throws IOException {
		setCORSHeaders(httpServletResponse); 
		httpServletResponse.setStatus(scForbidden);
		httpServletResponse.getWriter().write(string);
	}
	
}
