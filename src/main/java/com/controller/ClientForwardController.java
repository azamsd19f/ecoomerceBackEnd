package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientForwardController {

	@GetMapping(value = "/**/{path:[^\\.]*}")
	public String forward() {
		return "forward:/";
	}

}
