package com.vithal.electronic.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.vithal.electronic.dtos.PagebleResponse;

public class Helper {

	public static <U,V> PagebleResponse<V> getPAgebaleResponse(Page<U> page,Class<V> type ){
	
		//assume U->User entity and V->DTO
List<U> entity = page.getContent();
		
		
		List<V> userDtos = entity.stream().map(obj->new ModelMapper().map(obj, type)).collect(Collectors.toList());
	
		PagebleResponse<V> response=new PagebleResponse<>();
		response.setContents(userDtos);
		response.setLastPage(page.isLast());
		response.setPageNumber(page.getNumber());
		response.setTotalPages(page.getTotalPages());
		response.setTotalElements(page.getTotalElements());
		response.setPageSize(page.getSize());
		
		
		return response;
		
		
	}
}
