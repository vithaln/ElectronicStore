package com.vithal.electronic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vithal.electronic.entities.User;

public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findByEmail(String email);
	
	List<User> findByNameContaining(String keywords);
}
