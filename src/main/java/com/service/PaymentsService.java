package com.service;

import java.util.List;
import java.util.Map;

import com.dto.OrderStatus;
import com.dto.PaymentDto;

public interface PaymentsService {

	public String InitiateTransaction(Integer userId, Double amount);

	public String updateOrder(String orderId, Map<String, String> data);

	public List<PaymentDto> findTransactionByUser(Integer userId);

	public Map<Object, Map<Object, List<PaymentDto>>> getMonthlyData();

	public Map<String, Double> getMonthwiseIncomeSummary();

	public List<PaymentDto> getDataByMonths();

	public OrderStatus getOrderDataCount();

}
