package com.kodnest.tradeNest.Services;

import java.util.ArrayList; 
import java.util.List;

import org.springframework.stereotype.Service;

import com.kodnest.tradeNest.Entitys.Categories;
import com.kodnest.tradeNest.Entitys.Productimages;
import com.kodnest.tradeNest.Entitys.Productss;
import com.kodnest.tradeNest.Repository.CategoriesRepository;
import com.kodnest.tradeNest.Repository.ProductImageRepository;
import com.kodnest.tradeNest.Repository.productsRepository;
import com.kodnest.tradeNest.serviceContract.ProductServiceContract;
@Service
public class ProductService implements ProductServiceContract{
	private CategoriesRepository categoriesRepository;
	private productsRepository productsRepository;
	private ProductImageRepository imageRepository; 

	

	public ProductService(CategoriesRepository categoriesRepository,
			com.kodnest.tradeNest.Repository.productsRepository productsRepository,
			ProductImageRepository imageRepository) {
		super();
		this.categoriesRepository = categoriesRepository;
		this.productsRepository = productsRepository;
		this.imageRepository = imageRepository;
	}

	@Override
	public List<Productss> getProductsByCatogrey(String categories) {
		if(!categories.isEmpty() || categories != null) {
			Categories categ = categoriesRepository.findByCategoryName(categories);
			if(categ != null) {
				List<Productss> productsses = productsRepository.findByCategories_CategoryId(categ.getCategoryId());
				return productsses;
			} else {
				throw new RuntimeException("Catagorey not found");
			}
		} else {
				return productsRepository.findAll();
			}
	}

	@Override
	public List<String> getProductsProductsImage(Productss productss) {
		List<Productimages> productimages = imageRepository.findByProductss_ProductId(productss.getProductId());
		List<String> image = new ArrayList<>();
		for(Productimages productimages2 : productimages) {
			image.add(productimages2.getImageUrl());
		}
		return image;		
	}

}
