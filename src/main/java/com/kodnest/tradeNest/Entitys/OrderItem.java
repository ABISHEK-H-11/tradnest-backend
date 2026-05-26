package com.kodnest.tradeNest.Entitys;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Productss productss;
	@Column(name = "quantity")
	private int quantity;
	@Column(name = "price_per_unit")
	private BigDecimal pricePerUnit;
	@Column(name = "total_price")
	private BigDecimal totalPrice;
	
	public OrderItem() {
		// TODO Auto-generated constructor stub
	}

	public OrderItem(Integer id, Order order, Productss productss, int quantity, BigDecimal pricePerUnit,
			BigDecimal totalPrice) {
		super();
		this.id = id;
		this.order = order;
		this.productss = productss;
		this.quantity = quantity;
		this.pricePerUnit = pricePerUnit;
		this.totalPrice = totalPrice;
	}

	public OrderItem(Order order, Productss productss, int quantity, BigDecimal pricePerUnit, BigDecimal totalPrice) {
		super();
		this.order = order;
		this.productss = productss;
		this.quantity = quantity;
		this.pricePerUnit = pricePerUnit;
		this.totalPrice = totalPrice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Productss getProductss() {
		return productss;
	}

	public void setProductss(Productss productss) {
		this.productss = productss;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", order=" + order + ", productss=" + productss + ", quantity=" + quantity
				+ ", pricePerUnit=" + pricePerUnit + ", totalPrice=" + totalPrice + "]";
	}
	
	
}
