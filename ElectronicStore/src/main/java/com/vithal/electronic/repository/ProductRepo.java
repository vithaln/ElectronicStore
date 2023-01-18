package com.vithal.electronic.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vithal.electronic.entities.Category;
import com.vithal.electronic.entities.Product;

public interface ProductRepo extends JpaRepository<Product, String> {

	Page<Product> findByTitleContaining(String subtitle,Pageable pageable);
	Page<Product> findByLiveTrue(Pageable pageable);
	
	Page<Product> findByCategory(Category category, Pageable pageable);
}
