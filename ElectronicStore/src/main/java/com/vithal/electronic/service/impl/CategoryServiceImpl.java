package com.vithal.electronic.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.vithal.electronic.dtos.CategoryDto;
import com.vithal.electronic.dtos.PagebleResponse;
import com.vithal.electronic.entities.Category;
import com.vithal.electronic.exceptions.ResourceNotFoudException;
import com.vithal.electronic.helper.Helper;
import com.vithal.electronic.repository.CategoryRepo;
import com.vithal.electronic.services.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper mapper;
	
	@Value("${category.profile.imageName}")
	private String imagePath;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		String cateId = UUID.randomUUID().toString();
		categoryDto.setCategoryId(cateId);
		Category category = mapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepo.save(category);
		CategoryDto categoryDtos = mapper.map(savedCategory, CategoryDto.class);
		return categoryDtos;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {

//find category by id
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoudException("Category not found with this id: " + categoryId));

		// update category by Id
		category.setTitle(categoryDto.getTitle());
		category.setDecription(categoryDto.getDecription());
		category.setCoverImage(categoryDto.getCoverImage());

		Category updatedCategory = categoryRepo.save(category);
		CategoryDto categoryDtoUpdated = mapper.map(updatedCategory, CategoryDto.class);
		return categoryDtoUpdated;
	}

	@Override
	public CategoryDto getSingleCate(String categoryId) {
		// find category by id
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoudException("Category not found with this id: " + categoryId));

		CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
		return categoryDto;
	}

	@Override
	public PagebleResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageble = PageRequest.of(pageNumber, pageSize, sort);
		Page<Category> page = categoryRepo.findAll(pageble);

		PagebleResponse<CategoryDto> pagebleResponse = Helper.getPAgebaleResponse(page, CategoryDto.class);
		return pagebleResponse;
	}

	@Override
	public void delteCategory(String categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoudException("Category not found with this id: " + categoryId));
		
		//delete category cover image 
				//images/category/vikki.png
				String fullImagePath=imagePath+category.getCoverImage();
				log.info("Image Fill path {}",fullImagePath);
				
				try {
					
				Path path=Paths.get(fullImagePath);
				Files.delete(path);
				
				}catch(NoSuchFileException ex) {
					ex.printStackTrace();
					log.info("Category cover Image not found in folder {}");
				}
				catch(IOException ex) {
					ex.printStackTrace();
				}
		
		categoryRepo.delete(category);
	}

	@Override
	public List<CategoryDto> getSearchByKeyword(String keyword) {

List<Category> findByNameContaining = categoryRepo.findByTitleContaining(keyword);
List<CategoryDto> categoryDtos = findByNameContaining.stream().map(cate-> this.mapper.map(cate, CategoryDto.class)).collect(Collectors.toList());
		return categoryDtos;
	}

}
