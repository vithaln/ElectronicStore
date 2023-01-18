package com.vithal.electronic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vithal.electronic.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, String> {

	
	List<Category> findByTitleContaining(String keyword);
	
}
