package com.kodnest.tradeNest.serviceContract;

import java.util.Map;

public interface AdminBusnessServiceContract {
	public Map<String, Object> getMonthlyBusiness(int month, int year);
	public Map<String, Object> getDailyBusiness(String data);
	public Map<String, Object> getYearlyBusiness(int year);
	public Map<String, Object> getOverAllBusiness();
}
