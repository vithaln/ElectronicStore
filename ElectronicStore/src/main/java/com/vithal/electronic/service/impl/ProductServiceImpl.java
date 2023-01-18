package com.vithal.electronic.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vithal.electronic.dtos.PagebleResponse;
import com.vithal.electronic.dtos.ProductDto;
import com.vithal.electronic.entities.Product;
import com.vithal.electronic.exceptions.ResourceNotFoudException;
import com.vithal.electronic.helper.Helper;
import com.vithal.electronic.repository.ProductRepo;
import com.vithal.electronic.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private ModelMapper mapper;
	@Value("${product.profile.imageName}")
	private String imagePath;

	@Override
	public ProductDto createProduct(ProductDto dto) {

		dto.setProductId(UUID.randomUUID().toString());
		dto.setAddedDate(new Date());
		Product product = mapper.map(dto, Product.class);

		Product savedProduct = productRepo.save(product);
		ProductDto productDto = mapper.map(savedProduct, ProductDto.class);
		return productDto;
	}

	@Override
	public ProductDto updateProduct(ProductDto dto, String productId) {

		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoudException("Product Not by using This Id: " + productId));
		product.setTitle(dto.getTitle());
		product.setDescription(dto.getDescription());
		product.setDiscountedPrice(dto.getDiscountedPrice());
		product.setAddedDate(dto.getAddedDate());
		product.setPrice(dto.getPrice());
		product.setQuantity(dto.getQuantity());
		product.setLive(dto.isLive());
		product.setStock(dto.isStock());
		product.setAddedDate(new Date());
		product.setProductImageName(dto.getProductImageName());

		Product updatedProduct = productRepo.save(product);
		ProductDto productDto = mapper.map(updatedProduct, ProductDto.class);
		return productDto;
	}

	@Override
	public ProductDto getSingleProduct(String productId) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoudException("Product Not by using This Id: " + productId));
		ProductDto productDto = mapper.map(product, ProductDto.class);
		return productDto;
	}

	@Override
	public void deleteProducts(String productId) {
		Product product = productRepo.findById(productId)
				.orElseThrow(() -> new ResourceNotFoudException("Product Not by using This Id: " + productId));
	
		
		String fullImagePath=imagePath+product.getProductImageName();
		
		try {
			
		Path path=Paths.get(fullImagePath);
		Files.delete(path);
		
		}catch(NoSuchFileException ex) {
			ex.printStackTrace();
			
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		
		productRepo.delete(product);
	}

	@Override
	public PagebleResponse<ProductDto> searchByTitle(String subtitile, int pageNumber, int pageSize, String sortBy,
			String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

		Pageable pageble = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepo.findByTitleContaining(subtitile, pageble);
		PagebleResponse<ProductDto> pagebleResponse = Helper.getPAgebaleResponse(page, ProductDto.class);
		return pagebleResponse;

	}

	@Override
	public PagebleResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

		Pageable pageble = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepo.findAll(pageble);
		PagebleResponse<ProductDto> pagebleResponse = Helper.getPAgebaleResponse(page, ProductDto.class);
		return pagebleResponse;

	}

	@Override
	public PagebleResponse<ProductDto> getAllProductLives(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

		Pageable pageble = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepo.findByLiveTrue(pageble);
		PagebleResponse<ProductDto> pagebleResponse = Helper.getPAgebaleResponse(page, ProductDto.class);
		return pagebleResponse;
	}

}
