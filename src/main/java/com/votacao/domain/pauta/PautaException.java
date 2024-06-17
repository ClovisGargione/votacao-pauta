package com.votacao.domain.pauta;

public class PautaException extends Exception {

	private static final long serialVersionUID = 1L;

	public PautaException() {
		super();
	}

	public PautaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PautaException(String message, Throwable cause) {
		super(message, cause);
	}

	public PautaException(String message) {
		super(message);
	}

	public PautaException(Throwable cause) {
		super(cause);
	}
	
	

}
