package com.votacao.domain.associado;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Associado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String cpf;

	public Associado() {
		super();
	}

	public Associado(Long id, String nome, String cpf) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
	}
	
	public Associado(String nome, String cpf) {
		super();
		this.nome = nome;
		this.cpf = cpf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf, id, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Associado other = (Associado) obj;
		return Objects.equals(cpf, other.cpf) && Objects.equals(id, other.id) && Objects.equals(nome, other.nome);
	}

	@Override
	public String toString() {
		return "Associado [id=" + id + ", nome=" + nome + ", cpf=" + cpf + "]";
	}
}
