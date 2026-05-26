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
@Table(name = "productimages")
public class Productimages {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer imageId;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Productss productss;
	@Column
	private String imageUrl;
	
	
	public Productimages() {
		// TODO Auto-generated constructor stub
	}


	public Productimages(Productss productss, String imageUrl) {
		super();
		this.productss = productss;
		this.imageUrl = imageUrl;
	}


	public Productimages(Integer imageId, Productss productss, String imageUrl) {
		super();
		this.imageId = imageId;
		this.productss = productss;
		this.imageUrl = imageUrl;
	}


	public Integer getImageId() {
		return imageId;
	}


	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}


	public Productss getProductss() {
		return productss;
	}


	public void setProductss(Productss productss) {
		this.productss = productss;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	@Override
	public String toString() {
		return "Productimages [imageId=" + imageId + ", productss=" + productss + ", imageUrl=" + imageUrl + "]";
	}
	
	
}
