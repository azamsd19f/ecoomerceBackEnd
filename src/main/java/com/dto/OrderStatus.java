package com.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatus {
	
	private Integer delivered;
	private Integer pending;
	private Integer totalReceivedOrder;

}
