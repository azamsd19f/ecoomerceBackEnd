package com.service.impls;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.dto.CategoryDto;
import com.dto.ProductDto;
import com.dto.ProductImageDto;
import com.entity.Category;
import com.entity.Product;
import com.entity.ProductImage;
import com.exception.ResourceNotFound;
import com.repository.CategoryRepository;
import com.repository.ProductRepository;
import com.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceimpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ProductRepository productRepository;

	@Value("${path.devServe}")
	private String devLink;

	@Override
	public Category createCategory(Category category) {
		if (category.getCategoryName() == null) {
			new ResourceNotFound("Please Fill Category Name properly");
		}
		Category categoryMap = categoryRepository.save(category);
		return categoryMap;
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		StringBuilder builders = new StringBuilder();
		builders.append(devLink).append("file/");
		log.info("builders {}", builders);
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFound("Category not found with ID: " + categoryId));
		// finding all products by category
		List<Product> listCategory = productRepository.findAllByCategory(category);

		List<ProductDto> productDtoList = listCategory.stream().map(product -> {
			ProductDto productDto = mapper.map(product, ProductDto.class);
			// Mapping product image to ProductImageDto

			ProductImageDto imageDto = mapper.map(product.getProductImage(), ProductImageDto.class);
			builders.append(imageDto.getImageName());
			String imageLink = builders.toString();
			log.info("image link {}", imageLink);
			imageDto.setLink(imageLink);
			productDto.setImageDto(imageDto);
			return productDto;
		}).collect(Collectors.toList());

		log.info("floor info {} :", productDtoList);
		CategoryDto dto = this.mapper.map(category, CategoryDto.class);

		dto.setProductDto(productDtoList);
//		dto.set(allProductImageDto);
		return dto;
	}

	// mapProduct
	public ProductDto mapProduct(Product product) {
		ProductDto productDto = new ProductDto();
		productDto.setPrice(product.getPrice());
		productDto.setProductName(product.getProductName());

		return productDto;
	}

	// mapProductImage
	public ProductImageDto mapProductImages(ProductImage images) {
		ProductImageDto imageDto = new ProductImageDto();
		imageDto.setImageId(images.getImageId());
		imageDto.setImageName(images.getImageName());

		return imageDto;
	}

}
