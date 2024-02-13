package com.service;
import com.dto.CategoryDto;
import com.entity.Category;

public interface CategoryService {

	public Category createCategory(Category category);

	public CategoryDto getCategoryById(Integer categoryId);
}
