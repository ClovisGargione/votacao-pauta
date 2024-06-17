package com.votacao.components;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ComponenteBase {

	private TipoEnum tipo;
	
	private String titulo;
	
	private List<Item> itens;

	public ComponenteBase() {
		super();
	}

	public ComponenteBase(TipoEnum tipo, String titulo, List<Item> itens) {
		super();
		this.tipo = tipo;
		this.titulo = titulo;
		this.itens = itens;
	}

	public TipoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoEnum tipo) {
		this.tipo = tipo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}
}
