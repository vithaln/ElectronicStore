package com.vithal.electronic.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
import com.vithal.electronic.dtos.UserDto;
import com.vithal.electronic.payload.ApiResponseMesg;
import com.vithal.electronic.payload.ImageResponse;
import com.vithal.electronic.services.CategoryService;
import com.vithal.electronic.services.FileService;
import com.vithal.electronic.services.ProductService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private FileService fileService;

	@Autowired
	private ProductService productService;

	@Value("${category.profile.imageName}")
	private String imageUploadPath;

	// create Category
	@PostMapping
	public ResponseEntity<CategoryDto> saveCategory(@Valid @RequestBody CategoryDto dto) {
		CategoryDto createCategory = categoryService.createCategory(dto);

		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);

	}

	// get all category
	@GetMapping
	public ResponseEntity<PagebleResponse<CategoryDto>> getAllCategories(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

	) {
		PagebleResponse<CategoryDto> allCategory = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortBy);

		return new ResponseEntity<PagebleResponse<CategoryDto>>(allCategory, HttpStatus.OK);
	}

	// get Single Category
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable String categoryId) {
		CategoryDto singleCate = categoryService.getSingleCate(categoryId);
		return new ResponseEntity<CategoryDto>(singleCate, HttpStatus.OK);
	}

	// update category
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable String categoryId) {
		CategoryDto updateCategory = categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
	}

	// delete category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponseMesg> deleteCategory(@PathVariable String categoryId) {
		categoryService.delteCategory(categoryId);

		ApiResponseMesg responseMesg = ApiResponseMesg.builder().message("Category has been deleted successfully!")
				.status(HttpStatus.OK).success(true).build();
		return new ResponseEntity<ApiResponseMesg>(responseMesg, HttpStatus.OK);
	}

	// search by name
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<CategoryDto>> getSearchByKeyword(@PathVariable String keyword) {
		List<CategoryDto> searchByKeyword = categoryService.getSearchByKeyword(keyword);

		return new ResponseEntity<List<CategoryDto>>(searchByKeyword, HttpStatus.OK);
	}

	// upload image
	@PostMapping("/image/{categoryId}")
	public ResponseEntity<ImageResponse> uploadImage(@PathVariable String categoryId,
			@RequestParam("cateImage") MultipartFile image

	) throws IOException {

		String imageName = fileService.uploadFile(image, imageUploadPath);

		CategoryDto singleCate = categoryService.getSingleCate(categoryId);
		singleCate.setCoverImage(imageName);
		CategoryDto category = categoryService.updateCategory(singleCate, categoryId);

		ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true)
				.message("Image has been uploaded successfully..").status(HttpStatus.CREATED).build();

		return new ResponseEntity<ImageResponse>(imageResponse, HttpStatus.CREATED);

	}

	// serve image
	@GetMapping(value = "/image/{categoryId}")
	public void fetchImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {

		CategoryDto singleCate = categoryService.getSingleCate(categoryId);
		log.info("CategoryImage Image {} ", singleCate.getCoverImage());

		InputStream resource = fileService.getResource(imageUploadPath, singleCate.getCoverImage());
		log.info("Image resousource: {}", resource);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

	// create products with categoryId
	@PostMapping("/{categoryId}/products")
	public ResponseEntity<ProductDto> createProductWithCategory(@PathVariable String categoryId,
			@RequestBody ProductDto dto) {

		ProductDto createProductWithCategoryId = productService.createProductWithCategoryId(dto, categoryId);
		return new ResponseEntity<ProductDto>(createProductWithCategoryId, HttpStatus.CREATED);

	}

	// update category
	@PutMapping("/{categoryId}/product/{productId}")
	public ResponseEntity<ProductDto> updateCategory(@PathVariable String categoryId, @PathVariable String productId) {
		ProductDto updateCategory = productService.updateCategory(categoryId, productId);

		return new ResponseEntity<ProductDto>(updateCategory, HttpStatus.OK);
	}

	// get all products by category id
	@GetMapping("/{categoryId}/products")
	public ResponseEntity<PagebleResponse<ProductDto>> getAllOfProductsInCategories(@PathVariable String categoryId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir

	) {

		PagebleResponse<ProductDto> allOfProductOfCategorries = productService.getAllOfProductOfCategorries(categoryId,
				pageNumber, pageSize, sortBy, sortDir);

		return new ResponseEntity<PagebleResponse<ProductDto>>(allOfProductOfCategorries, HttpStatus.OK);

	}
}
