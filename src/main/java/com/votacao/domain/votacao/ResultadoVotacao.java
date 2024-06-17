package com.votacao.domain.votacao;

import com.votacao.domain.pauta.Pauta;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class ResultadoVotacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "pautaId")
	private Pauta pauta;
	
	private int sim;
	
	private int nao;

	public ResultadoVotacao() {
		super();
	}

	public ResultadoVotacao(Long id, Pauta pauta, int sim, int nao) {
		super();
		this.id = id;
		this.pauta = pauta;
		this.sim = sim;
		this.nao = nao;
	}

	public ResultadoVotacao(Pauta pauta, int sim, int nao) {
		this.pauta = pauta;
		this.sim = sim;
		this.nao = nao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pauta getPauta() {
		return pauta;
	}

	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}

	public int getSim() {
		return sim;
	}

	public void setSim(int sim) {
		this.sim = sim;
	}

	public int getNao() {
		return nao;
	}

	public void setNao(int nao) {
		this.nao = nao;
	}
}
