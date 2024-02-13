package com.dto;

import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

	private Integer userId;
	private String name;
	private String email;
	private String password;
	private boolean loggedInStatus;
	
	private List<PaymentDto> paymentDto ;
}
