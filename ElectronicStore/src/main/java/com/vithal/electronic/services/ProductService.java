package com.vithal.electronic.services;

import com.vithal.electronic.dtos.PagebleResponse;
import com.vithal.electronic.dtos.ProductDto;

public interface ProductService {

	ProductDto createProduct(ProductDto dto);
	ProductDto updateProduct(ProductDto dto,String productId);
	ProductDto getSingleProduct(String productId);
	void deleteProducts(String productId);
	//search
	PagebleResponse<ProductDto> searchByTitle(String subtitile,int pageNumber, int pageSize,String sortBy,String sortDir);
	PagebleResponse<ProductDto> getAllProducts(int pageNumber, int pageSize,String sortBy,String sortDir);
	PagebleResponse<ProductDto> getAllProductLives(int pageNumber, int pageSize,String sortBy,String sortDir);

	//createProductWithCategoryId
	
	ProductDto createProductWithCategoryId(ProductDto dto,String categoryId);
	
	//update category in products
	
	ProductDto updateCategory(String categoryId,String productId);
	
	//get all product of category
	//it is for in one categories how many products we have that's what we get.
	PagebleResponse<ProductDto> getAllOfProductOfCategorries(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);
	
}
