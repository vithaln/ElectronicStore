package com.vithal.electronic.dtos;

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
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String gender;
	
	private String about;
	
	private String imageName;


}
