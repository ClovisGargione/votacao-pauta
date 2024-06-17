package com.votacao.components;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Botao {

	private String texto;
	
	private String url;
	
	private Map<String, Object> body;

	public Botao() {
		super();
	}

	public Botao(String texto, String url) {
		super();
		this.texto = texto;
		this.url = url;
	}
	
	public Botao(String texto, String url, Map<String, Object> body) {
		super();
		this.texto = texto;
		this.url = url;
		this.body = body;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
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
