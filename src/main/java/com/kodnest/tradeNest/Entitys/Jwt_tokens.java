	package com.kodnest.tradeNest.Entitys;
	
	import java.time.LocalDateTime;
	
	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.JoinColumn;
	import jakarta.persistence.ManyToOne;
	import jakarta.persistence.OneToOne;
	import jakarta.persistence.Table;
	
	@Entity
	@Table(name = "jwt_tokens")
	public class Jwt_tokens {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer token_id;
		@ManyToOne
		@JoinColumn(name = "user_id", unique = true)
		private User user;
		@Column
		private String token;
		@Column
		private LocalDateTime expires_at;
		
		public Jwt_tokens() {
			// TODO Auto-generated constructor stub
		}
	
		public Jwt_tokens(com.kodnest.tradeNest.Entitys.User user, String token, LocalDateTime expires_at) {
			super();
			this.user = user;
			this.token = token;
			this.expires_at = expires_at;
		}
	
		public Jwt_tokens(Integer token_id, com.kodnest.tradeNest.Entitys.User user, String token,
				LocalDateTime expires_at) {
			super();
			this.token_id = token_id;
			this.user = user;
			this.token = token;
			this.expires_at = expires_at;
		}
	
		public Integer getToken_id() {
			return token_id;
		}
	
		public void setToken_id(Integer token_id) {
			this.token_id = token_id;
		}
	
		public User getUser() {
			return user;
		}
	
		public void setUser(User user) {
			this.user = user;
		}
	
		public String getToken() {
			return token;
		}
	
		public void setToken(String token) {
			this.token = token;
		}
	
		public LocalDateTime getExpires_at() {
			return expires_at;
		}
	
		public void setExpires_at(LocalDateTime expires_at) {
			this.expires_at = expires_at;
		}
	
		@Override
		public String toString() {
			return "Jwt_tokens [token_id=" + token_id + ", user=" + user + ", token=" + token + ", expires_at=" + expires_at
					+ "]";
		}
	
		
		
	}
