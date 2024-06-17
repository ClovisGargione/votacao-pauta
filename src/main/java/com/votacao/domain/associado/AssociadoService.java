package com.votacao.domain.associado;

import static java.util.Objects.nonNull;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacao.components.ComponenteBase;
import com.votacao.components.ComponenteException;
import com.votacao.components.Item;
import com.votacao.components.TipoEnum;
import com.votacao.domain.associado.dto.AssociadoDto;
import com.votacao.service.AService;
import com.votacao.service.Paths;
import com.votacao.service.UrlDominio;

@Service
public class AssociadoService extends AService {

	@Autowired
	private AssociadoRepository associadoRepository;

	@Autowired
	private UrlDominio urlDominio;

	public ComponenteBase montarListaAssociados() throws ComponenteException {
		List<Associado> associados = associadoRepository.findAll();
		ComponenteBase componente = new ComponenteBase();
		componente.setTipo(TipoEnum.SELECAO);
		componente.setTitulo("Lista de associados");
		componente.setItens(montarLista(associados));
		return componente;
	}

	public ComponenteBase montarOpcoes(AssociadoDto associadoDto) throws ComponenteException, AssociadoException {
		Associado associado = getAssociado(associadoDto.getIdAssociado());
		ComponenteBase componenteBase = new ComponenteBase();
		if (nonNull(associado)) {
			componenteBase.setTipo(TipoEnum.SELECAO);
			componenteBase.setTitulo("Opções do associado ".concat(associado.getNome()));
			try {
				List<Item> itens = new ArrayList<Item>();
				itens.add(new Item("Selecionar pauta", urlDominio.montarUrl(Paths.PATH_PAUTAS),
						getBody(associado.getId())));
				itens.add(new Item("Cadastrar pauta", urlDominio.montarUrl(Paths.PATH_FORM_PAUTA),
						getBody(associado.getId())));
				componenteBase.setItens(itens);
			} catch (UnknownHostException e) {
				throw new ComponenteException(e.getMessage(), e.getCause());
			}
		} else {
			throw new ComponenteException("Associado não encontrado");
		}
		return componenteBase;

	}

	public Associado getAssociado(Long idAssociado) throws AssociadoException {
		try {
			return associadoRepository.findById(idAssociado).orElseThrow();
		} catch (NoSuchElementException e) {
			throw new AssociadoException("Associado não encontrado");
		}
	}

	private List<Item> montarLista(List<Associado> associados) throws ComponenteException {
		return associados.stream().map(associado -> {
			try {
				return new Item(associado.getNome(), urlDominio.montarUrl(Paths.PATH_OPCOES_ASSOCIADO),
						getBody(associado.getId()));
			} catch (UnknownHostException e) {
				throw new ComponenteException(e.getMessage(), e.getCause());
			}
		}).collect(Collectors.toList());
	}
}
