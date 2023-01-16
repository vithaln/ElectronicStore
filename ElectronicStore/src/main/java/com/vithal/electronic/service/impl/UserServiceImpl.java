package com.vithal.electronic.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vithal.electronic.dtos.UserDto;
import com.vithal.electronic.entities.User;
import com.vithal.electronic.repository.UserRepository;
import com.vithal.electronic.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
	
		String userId = UUID.randomUUID().toString();
		userDto.setUserId(userId);
		User user = dtoToEntity(userDto);
		User savedUser = repository.save(user);
		
		UserDto userDtos = this.entityToDto(savedUser);
		
		return userDtos;
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = repository.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.entityToDto(user)).collect(Collectors.toList());
	
		return userDtos;
	}

	@Override
	public UserDto getUserById(String userId) {
		User user = repository.findById(userId).orElseThrow(()-> new RuntimeException("User Not Found with this Id "+userId));

		UserDto userDto = entityToDto(user);
		return userDto;
	}

	@Override
	public UserDto getUserByEmail(String email) {
	
		User user = repository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found with this email: "+email));
		UserDto userDtos = entityToDto(user);
		return userDtos;
	}

	@Override
	public List<UserDto> getUserByKeyWord(String keyword) {

List<User> users = repository.findByNameContaining(keyword);
List<UserDto> userDtos = users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public UserDto updateUserByUserId(UserDto userDto, String userId) {

		User user = repository.findById(userId).orElseThrow(()-> new RuntimeException("User Not Found with this Id "+userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setGender(userDto.getGender());
		user.setImageName(userDto.getImageName());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = repository.save(user);
		UserDto updatedDtos = this.entityToDto(updatedUser);
		return updatedDtos;
	}

	@Override
	public void delteUserByUserId(String userId) {
		User usr=repository.findById(userId).orElseThrow(()-> new RuntimeException("User Not Found with this Id "+userId));
repository.delete(usr);
		
	}
	
	//mapping methods
	public User dtoToEntity(UserDto dto) {
		/*
		 * User user = User.builder() .userId(dto.getUserId()) .name(dto.getName())
		 * .email(dto.getEmail()) .password(dto.getPassword()) .gender(dto.getGender())
		 * .imageName(dto.getImageName()) .about(dto.getAbout()).build();
		 */
		User user = mapper.map(dto, User.class);
		return user;
	}
	
	public UserDto entityToDto(User user) {
		
		/*
		 * UserDto userDto = UserDto.builder() .userId(user.getUserId())
		 * .name(user.getName()) .email(user.getEmail()) .password(user.getPassword())
		 * .gender(user.getGender()) .imageName(user.getImageName())
		 * .about(user.getAbout()).build();
		 */
		
		UserDto userDtos = mapper.map(user, UserDto.class);
		return userDtos;
	}

}