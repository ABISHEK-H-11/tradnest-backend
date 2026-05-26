package com.kodnest.tradeNest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kodnest.tradeNest.Entitys.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer>{
	public Categories findByCategoryName(String categoryName);
}
