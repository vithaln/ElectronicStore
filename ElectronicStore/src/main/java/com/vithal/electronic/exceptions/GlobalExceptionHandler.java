package com.vithal.electronic.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vithal.electronic.payload.ApiResponseMesg;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoudException.class)
	public ResponseEntity<ApiResponseMesg> resourcseNotFoundExceptionHandler(ResourceNotFoudException ex){
		
		ApiResponseMesg apiResponseMesg = ApiResponseMesg.builder()
		.message(ex.getMessage())
		.status(HttpStatus.NOT_FOUND)
		.success(true).build();
		return new  ResponseEntity<ApiResponseMesg>(apiResponseMesg,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,Object>> methodArgumentNotValid(MethodArgumentNotValidException ex){
	
		Map<String,Object> response=new HashMap<>();
		
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
		allErrors.stream().forEach(objError->{
			
			String message=objError.getDefaultMessage();
			String field = ((FieldError)objError).getField();
			response.put(message, field);
			
		});
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
	}

}
