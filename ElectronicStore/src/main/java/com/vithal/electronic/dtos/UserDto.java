package com.vithal.electronic.dtos;

import com.vithal.electronic.validate.ImageNameValid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
	

	
	private String userId;
	
	@Size(min = 3,max = 20,message = "Invalid Name!!")
	private String name;
	
	//@Email(message = "Invalid  User Email!!")
@Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",message = "Invalid User Email!")
	@NotBlank(message = "Email is Required!!")
	private String email;
	
	@NotBlank(message = "Password is required!!")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$",message = "password must contain 1 number (0-9)\r\n"
			+ "password must contain 1 uppercase letters\r\n"
			+ "password must contain 1 lowercase letters\r\n"
			+ "password must contain 1 non-alpha numeric number\r\n"
			+ "password is 8-16 characters with no space")
	private String password;
	
	@Size(min = 4,max = 6,message = "Invalid Gender!!")
	private String gender;
	
	@NotBlank(message = "please write something about yourself!!")
	private String about;
	
	@ImageNameValid
	private String imageName;


}
