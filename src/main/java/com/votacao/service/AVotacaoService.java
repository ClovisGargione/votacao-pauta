package com.votacao.service;

import java.util.ArrayList;
import java.util.List;

import com.votacao.components.Item;
import com.votacao.components.TipoEnum;
import com.votacao.domain.votacao.ResultadoVotacao;

public abstract class AVotacaoService extends APautaService {
	
	public List<Item> montarItens(ResultadoVotacao resultado) {
		List<Item> itens = new ArrayList<Item>();
		itens.add(new Item(TipoEnum.TEXTO, resultado.getPauta().getDescricao()));
		itens.add(new Item(TipoEnum.TEXTO, "Sim: ".concat(String.valueOf(resultado.getSim()))));
		itens.add(new Item(TipoEnum.TEXTO, "Não: ".concat(String.valueOf(resultado.getNao()))));
		return itens;
	}

}
