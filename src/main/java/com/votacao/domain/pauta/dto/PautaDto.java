package com.votacao.domain.pauta.dto;

public class PautaDto {
	
	private Long idAssociado;
		
	private String descricao;
	
	private Integer minutosDuracao;

	public PautaDto() {
		super();
	}

	public PautaDto(Long idAssociado, String descricao, Integer minutosDuracao) {
		super();
		this.idAssociado = idAssociado;
		this.descricao = descricao;
		this.minutosDuracao = minutosDuracao;
	}

	public Long getIdAssociado() {
		return idAssociado;
	}

	public void setIdAssociado(Long idAssociado) {
		this.idAssociado = idAssociado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getMinutosDuracao() {
		return minutosDuracao;
	}

	public void setMinutosDuracao(Integer minutosDuracao) {
		this.minutosDuracao = minutosDuracao;
	}
}
