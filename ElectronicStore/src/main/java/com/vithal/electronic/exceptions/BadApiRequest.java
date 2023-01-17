package com.vithal.electronic.exceptions;

public class BadApiRequest extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BadApiRequest() {
		super("File Extension is not found!");
	}
	public BadApiRequest(String message) {
		super(message);
	}

}
