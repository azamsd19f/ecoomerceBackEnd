package com.service;

import java.util.List;

import com.dto.CategoryDto;
import com.dto.ProductDto;
import com.dto.ProductImageDto;
import com.entity.Product;

public interface ProductService {
	
	public ProductDto getProductById(Integer productId);
	
	public ProductDto addProduct(ProductDto productDto, Integer categoryId);
	
	public String  updateOneProduct (Integer productId, Product product) ;
	
	public List<ProductDto> getAllProduct();
	
	public ProductImageDto addProductImage(ProductImageDto dto,Integer productId);
	
	public ProductImageDto findProductImage(Long imageId);
	

	
		
	
	
	
	

}
