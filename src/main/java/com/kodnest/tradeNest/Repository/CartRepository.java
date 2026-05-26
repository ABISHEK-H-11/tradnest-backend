package com.kodnest.tradeNest.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kodnest.tradeNest.Entitys.Cart_items;

import jakarta.transaction.Transactional;

@Repository
public interface CartRepository extends JpaRepository<Cart_items, Integer>{
	@Query("select c from Cart_items c where c.products.productId = :productId and c.user.userId =:userId")
	public Cart_items findByProductIdAndUserId(Integer productId, Integer userId);
	
	@Query("select c from Cart_items c where c.user.userId = :userId")
	public List<Cart_items> findByUserId(Integer userId);
	
	@Query("Select COALESCE(sum(c.quantity),0) from Cart_items c where  c.user.userId = :userId")
	public int findCartCount(int userId);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Cart_items c where c.user.userId = :userId")
	public void deletCartUsingUserId(Integer userId);
}
