package com.votacao.domain.associado.dto;

public class AssociadoDto {
	
	private Long idAssociado;
	
	public AssociadoDto() {
		super();
	}

	public AssociadoDto(Long idAssociado) {
		super();
		this.idAssociado = idAssociado;
	}

	public Long getIdAssociado() {
		return idAssociado;
	}

	public void setIdAssociado(Long idAssociado) {
		this.idAssociado = idAssociado;
	}
}
