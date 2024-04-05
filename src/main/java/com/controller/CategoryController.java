package com.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CategoryDto;
import com.entity.Category;
import com.exception.InputValidationEx;
import com.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/category/")
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService service;

	@PostMapping
	public ResponseEntity<Category> saveCategory(@RequestBody Category category) {

		if (category.getCategoryName().isEmpty()) {
			throw new InputValidationEx("Category Name Can Not Null");
		}
		service.createCategory(category);
		return ResponseEntity.status(HttpStatus.CREATED).body(category);
	}
	@GetMapping("{categoryId}")
	public ResponseEntity<CategoryDto> getByCategoryId(@PathVariable Integer categoryId){
	
		return ResponseEntity.status(HttpStatus.OK).body(service.getCategoryById(categoryId));
		
	}
}
