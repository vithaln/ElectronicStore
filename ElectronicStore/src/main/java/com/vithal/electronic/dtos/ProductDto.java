package com.vithal.electronic.dtos;

import java.util.Date;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

	private String productId;
	private String title;
	private String description;
	private int quantity;
	private int price;
	private int discountedPrice;
	private Date addedDate;
	private boolean live;
	private boolean stock;
	private String productImageName;
	
	private CategoryDto category;

}
