package com.votacao.components;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ComponenteForm extends ComponenteBase {

	private Botao botaoOk;
	
	private Botao botaoCancelar;

	public ComponenteForm() {
		super();
	}

	public ComponenteForm(TipoEnum tipo, String titulo, List<Item> itens, Botao botaoOk, Botao botaoCancelar) {
		super(tipo, titulo, itens);
		this.botaoOk = botaoOk;
		this.botaoCancelar = botaoCancelar;
	}

	public Botao getBotaoOk() {
		return botaoOk;
	}

	public void setBotaoOk(Botao botaoOk) {
		this.botaoOk = botaoOk;
	}

	public Botao getBotaoCancelar() {
		return botaoCancelar;
	}

	public void setBotaoCancelar(Botao botaoCancelar) {
		this.botaoCancelar = botaoCancelar;
	}
	
}
