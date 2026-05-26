package com.kodnest.tradeNest.Services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kodnest.tradeNest.Entitys.Categories;
import com.kodnest.tradeNest.Entitys.Productimages;
import com.kodnest.tradeNest.Entitys.Productss;
import com.kodnest.tradeNest.Repository.CategoriesRepository;
import com.kodnest.tradeNest.Repository.ProductImageRepository;
import com.kodnest.tradeNest.Repository.productsRepository;
import com.kodnest.tradeNest.serviceContract.AdminPorductServiceContract;

@Service
public class AdminPorductService implements AdminPorductServiceContract{
	
	private CategoriesRepository categoriesRepository;
	private productsRepository productsRepository;
	private ProductImageRepository productImageRepository;
	
	
	public AdminPorductService(CategoriesRepository categoriesRepository,
			com.kodnest.tradeNest.Repository.productsRepository productsRepository,
			ProductImageRepository productImageRepository) {
		super();
		this.categoriesRepository = categoriesRepository;
		this.productsRepository = productsRepository;
		this.productImageRepository = productImageRepository;
	}


	@Override
	public Productss saveTheProducts(String name, String description, Double price, Integer categoryId, String imageUrl,
			Integer stock) {
		Optional<Categories> categories = categoriesRepository.findById(categoryId);
		if(!categories.isEmpty()) {
			Categories ValidCategories = categories.get();
			Productss productss = new Productss(name, description, BigDecimal.valueOf(price), stock, ValidCategories, LocalDateTime.now(), LocalDateTime.now());
			productsRepository.save(productss);
			if(imageUrl != null || !imageUrl.isEmpty()) {
				Productimages productimages = new Productimages(productss, imageUrl);
				productImageRepository.save(productimages);
			}
			return productss;
		}
		return null;
	}

	@Override
	public void deletProduct(Integer prodctId) {
		 Optional<Productss> productss = productsRepository.findById(prodctId);
		 if(!productss.isEmpty()) {
			 Productss verifideProduct = productss.get();
			 productsRepository.delete(verifideProduct);
			 List<Productimages> productimages = productImageRepository.findByProductss_ProductId(prodctId);
			 for(Productimages productimages2 : productimages) {
				 productImageRepository.delete(productimages2);
			 }
		 }
	}

}
