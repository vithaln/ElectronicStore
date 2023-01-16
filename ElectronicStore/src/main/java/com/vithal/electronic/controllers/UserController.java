package com.vithal.electronic.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vithal.electronic.dtos.UserDto;
import com.vithal.electronic.payload.ApiResponseMesg;
import com.vithal.electronic.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;
	
	//create users
	@PostMapping("/")
	public ResponseEntity<UserDto> saveUsers(@RequestBody UserDto dto){
		
		UserDto createUser = service.createUser(dto);
		return new ResponseEntity<UserDto>(createUser,HttpStatus.CREATED);
	}
	
	//get All users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> fetchAllUsers(){
		List<UserDto> allUsers = service.getAllUsers();
		return new ResponseEntity<List<UserDto>>(allUsers,HttpStatus.OK);
	}
	
	//get single user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> fetchSingleUserByUserId(@PathVariable String userId){
		
		UserDto userDto = service.getUserById(userId);
		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
	}
	
	//update user by id
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUserById(@RequestBody UserDto dto,@PathVariable String userId){
		
		UserDto updateUserByUserId = service.updateUserByUserId(dto, userId);
		return new ResponseEntity<UserDto>(updateUserByUserId,HttpStatus.OK);
	}
	
	//get user by email
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> fetchUserByEmail(@PathVariable String email){
		UserDto userByEmail = service.getUserByEmail(email);
		return new ResponseEntity<UserDto>(userByEmail,HttpStatus.OK);
	}
	
	//get user by search name
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<UserDto>> fetchUsersByNameConatingkayword(@PathVariable String keyword){
		
		List<UserDto> userByKeyWord = service.getUserByKeyWord(keyword);
		return new ResponseEntity<List<UserDto>>(userByKeyWord,HttpStatus.OK);
	}
	
	//delete user by Id
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMesg> deleteUsersById(@PathVariable String userId){
		
		ApiResponseMesg apiResponseMesg = ApiResponseMesg.builder()
		.message("User deleted successfully!!")
		.success(true)
		.status(HttpStatus.OK).build();
		service.delteUserByUserId(userId);
		return new ResponseEntity<ApiResponseMesg>(apiResponseMesg,HttpStatus.OK);
	}
}
