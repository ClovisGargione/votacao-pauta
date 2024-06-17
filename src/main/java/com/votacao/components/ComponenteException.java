package com.votacao.components;

public class ComponenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ComponenteException() {
		super();
	}

	public ComponenteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ComponenteException(String message, Throwable cause) {
		super(message, cause);
	}

	public ComponenteException(String message) {
		super(message);
	}

	public ComponenteException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public synchronized Throwable getCause() {
		return super.getCause();
	}
	
	

}
