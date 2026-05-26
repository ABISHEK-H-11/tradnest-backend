package com.kodnest.tradeNest.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kodnest.tradeNest.Entitys.Jwt_tokens;
@Repository
public interface JwtRepository extends JpaRepository<Jwt_tokens, Integer>{
	@Query("select j from Jwt_tokens j where j.user.userId =:user_Id")
	public Jwt_tokens findByUserId(Integer user_Id);
	
	public Jwt_tokens findByToken(String token);
	
}
