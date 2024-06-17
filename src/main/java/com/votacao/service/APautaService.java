package com.votacao.service;

import java.util.ArrayList;
import java.util.List;

import com.votacao.components.Item;
import com.votacao.components.TipoEnum;
import com.votacao.domain.pauta.Pauta;

public abstract class APautaService extends AService {

	public List<Item> montarItens(Pauta pauta) {
		List<Item> itens = new ArrayList<Item>();
		itens.add(new Item(TipoEnum.TEXTO, pauta.getDescricao()));
		return itens;
	}
}
