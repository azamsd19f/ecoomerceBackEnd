package com.service.impls;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dto.ProductDto;
import com.dto.ProductImageDto;
import com.entity.Category;
import com.entity.Product;
import com.exception.ResourceNotFound;
import com.repository.CategoryRepository;
import com.repository.ProductRepository;
import com.service.CategoryService;
import com.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ModelMapper mapper;

	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public ProductDto getProductById(Integer productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFound("Product Id Not Found"));
		ProductDto dto = this.mapper.map(product, ProductDto.class);
		return dto;
	}

	@Override
	public ProductDto addProduct(ProductDto productDto, Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFound("Category" + "category id "));
		log.info("Before mapping: productDto = {}, categoryId = {}", productDto, categoryId);
		// Create a ModelMapper instance
		ModelMapper modelMapper = new ModelMapper();
		// Configure mapping for fields with the same name
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		// Map ProductDto to Product
		Product product = modelMapper.map(productDto, Product.class);

		// Set the category
		product.setCategory(category);

		log.info("After mapping: product = {}", product);

		// Save the product
		Product newProduct = this.productRepository.save(product);

		// Map the saved product back to ProductDto
		ProductDto newProductDto = modelMapper.map(newProduct, ProductDto.class);

		return newProductDto;
	}

	@Override
	public String updateOneProduct(Integer productId, Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductDto> getAllProduct() {
		List<Product> list = productRepository.findAll();
//		List<ProductDto> listDto = Arrays.asList(mapper.map(list, ProductDto[].class));
		
		List<ProductDto> productDtoList = list.stream()
	            .map(product -> {
	                ProductDto productDto = mapper.map(product, ProductDto.class);
	                // Mapping product image to ProductImageDto
	                ProductImageDto imageDto = mapper.map(product.getProductImage(), ProductImageDto.class);
	                productDto.setImageDto(imageDto);
	                return productDto;
	            })
	            .collect(Collectors.toList());

		return productDtoList;
	}

	@Override
	public ProductImageDto addProductImage(ProductImageDto dto, Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductImageDto findProductImage(Long imageId) {
		// TODO Auto-generated method stub
		return null;
	}
	

	

}
