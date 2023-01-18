package com.vithal.electronic.services;

import java.util.List;

import com.vithal.electronic.dtos.CategoryDto;
import com.vithal.electronic.dtos.PagebleResponse;

public interface CategoryService {

	
	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);
	CategoryDto getSingleCate(String categoryId);
	PagebleResponse<CategoryDto> getAllCategory(int pageNumber,int pageSize,String sortBy,String sortDir);
	void delteCategory(String categoryId);
	
	//search
	List<CategoryDto> getSearchByKeyword(String keyword);
	
}
