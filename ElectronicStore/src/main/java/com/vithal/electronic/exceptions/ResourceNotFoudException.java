package com.vithal.electronic.exceptions;

public class ResourceNotFoudException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoudException() {
		super("Resource Not Found Exception!!");
	}
	
	public ResourceNotFoudException(String message) {
		super(message);
	}
}
