package com.kodnest.tradeNest.serviceContract;

import java.math.BigDecimal;
import java.util.List;

import com.kodnest.tradeNest.Entitys.OrderItem;
import com.kodnest.tradeNest.Entitys.User;
import com.razorpay.RazorpayException;

public interface PaymentServiceContract {
	public String createOrder(User user, BigDecimal totalAmount) throws RazorpayException; 
	public boolean verifyPayment(String razorpayOrderId, 
			String razorpayPaymentId, 
			String razorpaySignature, User user) throws RazorpayException ;
}
