package com.votacao.domain.votacao;

public class VotacaoException extends Exception {

	private static final long serialVersionUID = 1L;

	public VotacaoException() {
		super();
	}

	public VotacaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public VotacaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public VotacaoException(String message) {
		super(message);
	}

	public VotacaoException(Throwable cause) {
		super(cause);
	}
	
	

}
