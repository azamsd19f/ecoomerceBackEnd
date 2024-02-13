package com.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.ProductDto;
import com.entity.Category;
import com.entity.Product;
import com.exception.ResourceNotFound;
import com.repository.CategoryRepository;
import com.repository.ProductRepository;
import com.service.ProductService;

import ch.qos.logback.core.model.Model;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/product/")
@Slf4j
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	
	@PostMapping("{categoryId}")
	public ProductDto createProduct(@RequestBody ProductDto dto, @PathVariable Integer categoryId) {
		log.info("Before mapping: productDto = {}, categoryId = {}", dto, categoryId);
		ProductDto d = productService.addProduct(dto, categoryId);
		return d;
	}

	@GetMapping("allProduct")
	public ResponseEntity<?> fetchAllProduct() {
		List<String> list = new ArrayList<>();
		list.add("No Product Available");
		list.add("Please Add Product");
		List<ProductDto> productList = productService.getAllProduct();
		if (productList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(productList);

		}

	}
	@GetMapping("{productId}")
	public ResponseEntity<ProductDto> findMyProduct(@PathVariable Integer productId) {
		return new ResponseEntity<ProductDto>(productService.getProductById(productId),HttpStatus.OK);
	}
}