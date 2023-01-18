package com.vithal.electronic.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CategoryDto {

	private String categoryId;
	@NotBlank
	@Size(min = 4,message = "title must be 4 character!")
	private String title;
	@NotBlank(message = "description must required!..")
	private String decription;
	private String coverImage;

}
