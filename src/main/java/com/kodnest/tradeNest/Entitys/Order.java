package com.kodnest.tradeNest.Entitys;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@Column(name = "order_id")
	private String orderId;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@Column(name = "total_amount")
	private BigDecimal totalAmount;
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;
	@Column(name = "created_at")
	private LocalDateTime created_at;
	@Column(name = "updated_at")
	private LocalDateTime updated_at;
	
	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(User user, BigDecimal totalAmount, Status status, LocalDateTime created_at, LocalDateTime updated_at) {
		super();
		this.user = user;
		this.totalAmount = totalAmount;
		this.status = status;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public Order(String orderId, User user, BigDecimal totalAmount, Status status, LocalDateTime created_at,
			LocalDateTime updated_at) {
		super();
		this.orderId = orderId;
		this.user = user;
		this.totalAmount = totalAmount;
		this.status = status;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", user=" + user + ", totalAmount=" + totalAmount + ", status=" + status
				+ ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
	}
	
}
