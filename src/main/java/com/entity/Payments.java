package com.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name="payments")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Payments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long myId;
	private String orderId;
	private Double amount;
	private String receipt;
	private String status;
	private String currency;

	private Double amountPaid;

	private Double amountDue;
	private Integer attempts;
	@CreatedDate
	private LocalDateTime createdAt;
	private String paymentId;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}
