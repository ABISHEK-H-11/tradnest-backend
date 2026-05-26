package com.kodnest.tradeNest.Entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_items")
public class Cart_items {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Productss products;
	@Column
	private int quantity;
	
	public Cart_items() {
		// TODO Auto-generated constructor stub
	}

	public Cart_items(Integer id, User user, Productss products, int quantity) {
		super();
		this.id = id;
		this.user = user;
		this.products = products;
		this.quantity = quantity;
	}

	public Cart_items(User user, Productss products, int quantity) {
		super();
		this.user = user;
		this.products = products;
		this.quantity = quantity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Productss getProducts() {
		return products;
	}

	public void setProducts(Productss products) {
		this.products = products;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Cart_items [id=" + id + ", user=" + user + ", products=" + products + ", quantity=" + quantity + "]";
	}

	
}
