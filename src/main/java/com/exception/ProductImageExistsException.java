package com.exception;

public class ProductImageExistsException extends RuntimeException {

	private static final long serialVersionUID = 8244783505354876979L;

	public ProductImageExistsException() {
		super("Image Already Uploaded For Product");
	}
	
	public ProductImageExistsException(String message) {
		super(message);
	}
}
