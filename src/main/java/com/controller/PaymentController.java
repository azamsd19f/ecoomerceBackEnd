package com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.OrderStatus;
import com.dto.PaymentDto;
import com.exception.ResourceNotFound;
import com.service.PaymentsService;

@RestController
@RequestMapping("/transaction/")
@CrossOrigin("http://localhost:3000")
public class PaymentController {

	@Autowired
	private PaymentsService paymentService;

	@GetMapping("get-GraphData")
	public Map<Object, Map<Object, List<PaymentDto>>> getMonthlyDataForGraph() {
		try {
			return this.paymentService.getMonthlyData();
		} catch (Exception e) {
			throw new ResourceNotFound("DATA NOT FOUND");
		}

	}

	@GetMapping("get-GraphDataByMonthName")
//	@Secured("hasRole('ADMIN')")
	public Map<String, Double> getMonthlyDataBar() {
		return this.paymentService.getMonthwiseIncomeSummary();
	}

	@GetMapping("ordersCount")
	public ResponseEntity<OrderStatus> getCountOfOrders() {
		OrderStatus os = paymentService.getOrderDataCount();
		return ResponseEntity.status(HttpStatus.OK).body(os);
	}
}
