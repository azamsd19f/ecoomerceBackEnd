package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.LoginResponse;
import com.dto.UserDto;
import com.service.UserService;

@RestController
@RequestMapping("/user/")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<String> saveUser(@RequestBody UserDto userDto) {
		userService.addUsers(userDto);
		return new ResponseEntity<String>("User Added Successfullly", HttpStatus.CREATED);
	}
	@GetMapping("{email}/{password}")
	public ResponseEntity<LoginResponse> loginUser(@PathVariable String email,@PathVariable String password) {
		LoginResponse response = userService.userLogin(email, password);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
