package com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFound.class)
	public ResponseEntity<ApiResponse> globalResponse(ResourceNotFound exception) {
		String message = exception.getMessage();
		ApiResponse response = ApiResponse.builder().message(message).status(HttpStatus.NOT_FOUND).build();

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InputValidationEx.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> invalidInputException(InputValidationEx ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
	
	
	@ExceptionHandler(ProductImageExistsException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> fileAlreadyUploadedException(ProductImageExistsException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

}
