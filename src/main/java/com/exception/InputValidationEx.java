package com.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputValidationEx extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;

	public InputValidationEx(String message) {
		this.message = message;
	}

}
