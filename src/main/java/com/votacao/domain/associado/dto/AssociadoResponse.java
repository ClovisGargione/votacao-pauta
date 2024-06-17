package com.votacao.domain.associado.dto;

public class AssociadoResponse {

	private String status;

	public AssociadoResponse() {
		super();
	}

	public AssociadoResponse(String status) {
		super();
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
