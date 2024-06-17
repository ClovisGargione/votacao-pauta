package com.votacao.domain.votacao;

import com.votacao.domain.associado.Associado;
import com.votacao.domain.pauta.Pauta;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Votacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "associadoId")
	private Associado associado;
	
	@ManyToOne
	@JoinColumn(name = "pautaId", unique = false)
	private Pauta pauta;
	
	private boolean voto;
	
	public Votacao() {
		super();
	}

	public Votacao(Associado associado, Pauta pauta, boolean voto) {
		super();
		this.associado = associado;
		this.pauta = pauta;
		this.voto = voto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Associado getAssociado() {
		return associado;
	}

	public void setAssociado(Associado associado) {
		this.associado = associado;
	}

	public Pauta getPauta() {
		return pauta;
	}

	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}

	public boolean isVoto() {
		return voto;
	}

	public void setVoto(boolean voto) {
		this.voto = voto;
	}
}
