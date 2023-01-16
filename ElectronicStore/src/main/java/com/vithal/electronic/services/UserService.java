package com.vithal.electronic.services;

import java.util.List;

import com.vithal.electronic.dtos.PagebleResponse;
import com.vithal.electronic.dtos.UserDto;

public interface UserService {

	
	//create User
	UserDto createUser(UserDto userDto);
	
	//get All users
	PagebleResponse<UserDto> getAllUsers(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	//get user by Id
	UserDto getUserById(String userId);
	
	// get user by Email
	UserDto getUserByEmail(String email);
	
	//search User
	List<UserDto> getUserByKeyWord(String keyword);
	
	//update User by Id
	UserDto updateUserByUserId(UserDto userDto,String userId);
	
	//delete use by user Id
	void delteUserByUserId(String userId);
	
	
}
