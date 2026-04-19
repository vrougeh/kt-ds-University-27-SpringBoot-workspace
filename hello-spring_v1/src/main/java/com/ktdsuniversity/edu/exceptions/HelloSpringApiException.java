package com.ktdsuniversity.edu.exceptions;

public class HelloSpringApiException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -233119508016065265L;

	private int errorStatus;
	private Object error;

	public HelloSpringApiException(String message, int errorStatus, Object error) {
		super(message);
		this.errorStatus = errorStatus;
		this.error = error;
	}

	public int getErrorStatus() {
		return this.errorStatus;
	}

	public Object geterror() {
		return this.error;
	}

}
