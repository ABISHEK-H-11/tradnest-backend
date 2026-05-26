package com.kodnest.tradeNest.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kodnest.tradeNest.Entitys.Productimages;

@Repository
public interface ProductImageRepository extends JpaRepository<Productimages, Integer>{
	public List<Productimages> findByProductss_ProductId(Integer productId);

}
