package com.vithal.electronic.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagebleResponse<T> {

	//T-->generic/general class
	private List<T> contents;
	private long totalElements;
	private int totalPages;
	private int pageNumber;
	private int pageSize;
	private boolean isLastPage;
}
