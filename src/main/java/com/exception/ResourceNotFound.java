package com.exception;

public class ResourceNotFound extends RuntimeException      {
	
	
	private static final long serialVersionUID = 8244783505354876979L;

	public ResourceNotFound() {
		super("Resource Not Found");
	}
	
	public ResourceNotFound(String message) {
		super(message);
	}

}
