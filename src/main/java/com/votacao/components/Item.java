package com.votacao.components;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(Include.NON_NULL)
public class Item {

	private String id;
	
	private TipoEnum tipo;
	
	private String texto;
	
	private String titulo;
	
	private Object valor;
	
	private String url;
	
	private Map<String, Object> body;

	public Item() {
		super();
	}

	public Item(String id, TipoEnum tipo, String texto, String titulo, Object valor, String url) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.texto = texto;
		this.titulo = titulo;
		this.valor = valor;
		this.url = url;
	}
	
	public Item(TipoEnum tipo, String titulo, String id, Object valor) {
		this.tipo = tipo;
		this.titulo = titulo;
		this.id = id;
		this.valor = valor;
	}
	
	public Item(String texto, String url, Map<String, Object> body) {
		this.texto = texto;
		this.url = url;
		this.body = body;
	}
	
	public Item(String texto, String url) {
		this.texto = texto;
		this.url = url;
	}
	
	public Item(TipoEnum tipo, String texto) {
		this.tipo = tipo;
		this.texto = texto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TipoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoEnum tipo) {
		this.tipo = tipo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Object> getBody() {
		return body;
	}

	public void setBody(Map<String, Object> body) {
		this.body = body;
	}
}
