package com.vithal.electronic.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
	
	@Id
	@Column(name="cate_Id")
	private String categoryId;
	@Column(name = "cate_Title",length = 60,nullable = false)
	private String title;
	@Column(name = "cate_Description",length = 60)
	private String decription;
	private String coverImage;

}
