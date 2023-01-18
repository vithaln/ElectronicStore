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

	
}
