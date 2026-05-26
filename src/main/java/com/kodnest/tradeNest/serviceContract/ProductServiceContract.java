package com.kodnest.tradeNest.serviceContract;

import java.util.List;

import com.kodnest.tradeNest.Entitys.Productimages;
import com.kodnest.tradeNest.Entitys.Productss;

public interface ProductServiceContract {
	List<Productss> getProductsByCatogrey(String categories); 
	List<String> getProductsProductsImage(Productss productss);
}
