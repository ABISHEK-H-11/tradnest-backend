package com.kodnest.tradeNest.serviceContract;

import com.kodnest.tradeNest.Entitys.Productss;

public interface AdminPorductServiceContract {
	public Productss saveTheProducts(String name, String description, Double price, Integer categoryId, String imageUrl, Integer stock);
	public void deletProduct(Integer prodctId);
}	
