package com.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PaymentDto {
	
	private Long myId;
	private String orderId;
	private Double amount;
	private String receipt;
	private String status;
	//private Users user;
	private LocalDateTime createdAt;
	private Double amountPaid;

	private Double amountDue;
	private String paymentId;
	private Integer attempt;

}
