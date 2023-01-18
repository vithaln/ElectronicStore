package com.vithal.electronic.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	@Id
	private String productId;
	private String title;
	@Column(length = 10000)
	private String description;
	private int quantity;
	private int price;
	private int discountedPrice;
	private Date addedDate;
	private boolean live;
	private boolean stock;
	private String productImageName;

}
