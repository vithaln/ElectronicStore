package com.vithal.electronic.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vithal.electronic.dtos.CategoryDto;
import com.vithal.electronic.dtos.PagebleResponse;
import com.vithal.electronic.dtos.ProductDto;
import com.vithal.electronic.payload.ApiResponseMesg;
import com.vithal.electronic.payload.ImageResponse;
import com.vithal.electronic.services.FileService;
import com.vithal.electronic.services.ProductService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

	@Autowired
	private ProductService productService;

	@Value("${product.profile.imageName}")
	private String imageUpload;

	@Autowired
	private FileService fileService;

	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto dto) {
		ProductDto createProduct = productService.createProduct(dto);
		return new ResponseEntity<ProductDto>(createProduct, HttpStatus.CREATED);
	}

	// update
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProducts(@RequestBody ProductDto dto, @PathVariable String productId) {
		ProductDto updateProduct = productService.updateProduct(dto, productId);

		return new ResponseEntity<ProductDto>(updateProduct, HttpStatus.OK);
	}

//get single product
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId) {

		ProductDto singleProduct = productService.getSingleProduct(productId);
		return new ResponseEntity<ProductDto>(singleProduct, HttpStatus.OK);
	}

	// delete product
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponseMesg> delteProduct(@PathVariable String productId) {

		ApiResponseMesg apiResponseMesg = ApiResponseMesg.builder().message("Product deleted successfully!!")
				.success(true).status(HttpStatus.OK).build();
		productService.deleteProducts(productId);
		return new ResponseEntity<ApiResponseMesg>(apiResponseMesg, HttpStatus.OK);
	}

	// get All product
	@GetMapping("/")
	public ResponseEntity<PagebleResponse<ProductDto>> getAllProducts(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PagebleResponse<ProductDto> allProducts = productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);

		return new ResponseEntity<PagebleResponse<ProductDto>>(allProducts, HttpStatus.OK);
	}

	// get products by live

	@GetMapping("/lives")
	public ResponseEntity<PagebleResponse<ProductDto>> getAllProductsLives(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PagebleResponse<ProductDto> allProductLives = productService.getAllProductLives(pageNumber, pageSize, sortBy,
				sortDir);

		return new ResponseEntity<PagebleResponse<ProductDto>>(allProductLives, HttpStatus.OK);
	}

	// search by subTitle
	@GetMapping("/subTitle/{query}")
	public ResponseEntity<PagebleResponse<ProductDto>> getAllProductSearchBysubTitle(@PathVariable String query,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PagebleResponse<ProductDto> allProductBysearch = productService.searchByTitle(query, pageNumber, pageSize,
				sortBy, sortDir);

		return new ResponseEntity<PagebleResponse<ProductDto>>(allProductBysearch, HttpStatus.OK);
	}

	// upload image
	@PostMapping("/image/{productId}")
	public ResponseEntity<ImageResponse> uploadImage(@PathVariable String productId,
			@RequestParam("productImage") MultipartFile image

	) throws IOException {

		String imageName = fileService.uploadFile(image, imageUpload);

		ProductDto singleProduct = productService.getSingleProduct(productId);
		singleProduct.setProductImageName(imageName);
		productService.updateProduct(singleProduct, productId);
		ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true)
				.message("Image has been uploaded successfully..").status(HttpStatus.CREATED).build();

		return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);

	}

	// serve image
	@GetMapping(value = "/image/{productId}")
	public void fetchImage(@PathVariable String productId, HttpServletResponse response) throws IOException {

		ProductDto singleProduct = productService.getSingleProduct(productId);

		log.info("CategoryImage Image {} ", singleProduct.getProductImageName());

		InputStream resource = fileService.getResource(imageUpload, singleProduct.getProductImageName());
		log.info("Image resousource: {}", resource);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
