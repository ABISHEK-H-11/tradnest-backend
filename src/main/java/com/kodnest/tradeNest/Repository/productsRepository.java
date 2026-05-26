package com.kodnest.tradeNest.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kodnest.tradeNest.Entitys.Productss;

@Repository
public interface productsRepository extends JpaRepository<Productss, Integer>{
	
	public List<Productss> findByCategories_CategoryId(Integer categoryId);
	
}
