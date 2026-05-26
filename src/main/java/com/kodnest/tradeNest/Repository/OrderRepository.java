package com.kodnest.tradeNest.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kodnest.tradeNest.Entitys.Order;
import com.kodnest.tradeNest.Entitys.OrderItem;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>{
	@Query("select oi from OrderItem oi where oi.order.user.userId = :userId and oi.order.status='SUCCESS'")
	public List<OrderItem> findByUserId(Integer userId);
	
	@Query("Select o from Order o where month(o.created_at) = :month and year(o.created_at) = :year and o.status = 'SUCCESS'")
	public List<Order> findMonthlyBusiness(int month, int year);
	
	@Query("Select o from Order o where date(o.created_at) = :date and o.status = 'SUCCESS'")
	public List<Order> findDailyBusiness(LocalDate date);
	
	@Query("Select o from Order o where year(o.created_at) = :year and o.status = 'SUCCESS'")
	public List<Order> findYearlyBusiness(int year);
	
	@Query("Select o from Order o where o.status = 'SUCCESS'")
	public List<Order> findByStatusSuccess();
}
