package com.votacao.domain.pauta;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pauta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descricao;
	
	private Integer minutosDuracao = 1;
	
	private boolean sessaoAberta = false;
	
	private LocalDateTime inicioVotacao;
	
	private LocalDateTime fimVotacao;

	public Pauta(Long id, String descricao, Integer minutosDuracao) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.minutosDuracao = minutosDuracao;
	}
	
	public Pauta(Long id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}
	
	public Pauta(String descricao, Integer minutosDuracao) {
		super();
		this.descricao = descricao;
		this.minutosDuracao = minutosDuracao;
	}

	public Pauta() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean isSessaoAberta() {
		return sessaoAberta;
	}

	public void setSessaoAberta(boolean sessaoAberta) {
		this.sessaoAberta = sessaoAberta;
	}

	public LocalDateTime getInicioVotacao() {
		return inicioVotacao;
	}

	public void setInicioVotacao(LocalDateTime inicioVotacao) {
		this.inicioVotacao = inicioVotacao;
	}

	public LocalDateTime getFimVotacao() {
		return fimVotacao;
	}

	public void setFimVotacao(LocalDateTime fimVotacao) {
		this.fimVotacao = fimVotacao;
	}
}
