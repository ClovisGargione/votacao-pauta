package com.votacao.domain.pauta;

import static java.util.Objects.isNull;

import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacao.components.Botao;
import com.votacao.components.ComponenteBase;
import com.votacao.components.ComponenteException;
import com.votacao.components.ComponenteForm;
import com.votacao.components.Item;
import com.votacao.components.TipoEnum;
import com.votacao.domain.associado.Associado;
import com.votacao.domain.associado.AssociadoException;
import com.votacao.domain.associado.AssociadoService;
import com.votacao.domain.associado.dto.AssociadoDto;
import com.votacao.domain.pauta.dto.PautaDto;
import com.votacao.service.APautaService;
import com.votacao.service.Paths;
import com.votacao.service.UrlDominio;

@Service
public class PautaService extends APautaService {

	private static final String MSG_ERRO_DESCRICAO = "A descrição deve ser informada";

	@Autowired
	private PautaRepository pautaRepository;

	@Autowired
	private AssociadoService associadoService;

	@Autowired
	private UrlDominio urlDominio;

	public ComponenteBase pautas(AssociadoDto associadoDto) throws ComponenteException, PautaException, AssociadoException {
		List<Pauta> pautas = pautaRepository.findAll();
		Associado associado = associadoService.getAssociado(associadoDto.getIdAssociado());
		ComponenteBase componenteBase = new ComponenteBase();
		componenteBase.setTipo(TipoEnum.SELECAO);
		componenteBase.setTitulo("Lista de pautas");
		componenteBase.setItens(montarLista(pautas, associado.getId()));
		return componenteBase;

	}

	public ComponenteForm montarFormPauta(AssociadoDto associadoDto) throws ComponenteException, AssociadoException {
		Associado associado = associadoService.getAssociado(associadoDto.getIdAssociado());
		ComponenteForm componenteForm = new ComponenteForm();
		componenteForm.setTipo(TipoEnum.FORMULARIO);
		componenteForm.setTitulo("Cadastrar pauta");
		componenteForm.setItens(montarItensForm());
		try {
			componenteForm.setBotaoOk(
					new Botao("Salvar", urlDominio.montarUrl(Paths.PATH_CADASTRAR_PAUTA), getBody(associado.getId())));
			componenteForm.setBotaoCancelar(new Botao("Cancelar", urlDominio.montarUrl(Paths.PATH_OPCOES_ASSOCIADO),
					getBody(associado.getId())));
		} catch (UnknownHostException e) {
			throw new ComponenteException(e.getMessage(), e.getCause());
		}
		return componenteForm;
	}

	public ComponenteBase cadastarPauta(PautaDto pautaDto) throws AssociadoException, PautaException, ComponenteException {
		Pauta pauta = validarCampos(pautaDto);
		Associado associado = associadoService.getAssociado(pautaDto.getIdAssociado());
		pautaRepository.save(pauta);
		return pautas(new AssociadoDto(associado.getId()));
	}

	public Pauta getPauta(Long idPauta) throws PautaException {
		try {
			return pautaRepository.findById(idPauta).orElseThrow();
		} catch (NoSuchElementException e) {
			throw new PautaException("Pauta não encontrada");
		}
	}

	public ComponenteForm formSessao(Long idPauta, AssociadoDto associadoDto)
			throws ComponenteException, PautaException, AssociadoException {
		Pauta pauta = getPauta(idPauta);
		Associado associado = associadoService.getAssociado(associadoDto.getIdAssociado());
		ComponenteForm componenteForm = new ComponenteForm();
		componenteForm.setTipo(TipoEnum.FORMULARIO);
		componenteForm.setTitulo("Abrir Sessão");
		componenteForm.setItens(montarItens(pauta));
		try {
			componenteForm.setBotaoOk(new Botao("Abrir Sessão",
					urlDominio.montarUrl(Paths.PATH_PAUTA_ABRIR_SESSAO.concat("/").concat(pauta.getId().toString())),
					getBody(associado.getId())));
			componenteForm.setBotaoCancelar(
					new Botao("Voltar", urlDominio.montarUrl(Paths.PATH_PAUTAS), getBody(associado.getId())));
		} catch (UnknownHostException e) {
			throw new ComponenteException(e.getMessage(), e.getCause());
		}

		return componenteForm;
	}

	public ComponenteBase abrirSessao(Long idPauta, AssociadoDto associadoDto)
			throws ComponenteException, PautaException, AssociadoException {
		Pauta pauta = getPauta(idPauta);
		Associado associado = associadoService.getAssociado(associadoDto.getIdAssociado());
		if (!pauta.isSessaoAberta()) {
			pauta.setSessaoAberta(true);
			pauta.setInicioVotacao(LocalDateTime.now(getZoneId()));
			pauta.setFimVotacao(pauta.getInicioVotacao().plusMinutes(pauta.getMinutosDuracao()));
			pautaRepository.save(pauta);
		}
		return pautas(new AssociadoDto(associado.getId()));
	}

	private Pauta validarCampos(PautaDto pautaDto) throws PautaException {
		if (pautaDto.getMinutosDuracao() <= 0 || isNull(pautaDto.getMinutosDuracao())) {
			pautaDto.setMinutosDuracao(1);
		}
		if (isNull(pautaDto.getDescricao()) || pautaDto.getDescricao().isBlank()) {
			throw new PautaException(MSG_ERRO_DESCRICAO);
		}
		return new Pauta(pautaDto.getDescricao(), pautaDto.getMinutosDuracao());
	}

	private List<Item> montarItensForm() {
		List<Item> itens = new ArrayList<Item>();
		itens.add(new Item(TipoEnum.INPUT_TEXTO, "Descrição da pauta", "descricao", ""));
		itens.add(new Item(TipoEnum.INPUT_NUMERO, "Tempo de votação em minutos", "minutosDuracao", 1));
		return itens;
	}

	private List<Item> montarLista(List<Pauta> pautas, Long idAssociado) throws ComponenteException {

		return pautas.stream().map(pauta -> {
			try {
				return new Item(pauta.getDescricao(),
						urlDominio.montarUrl(getPathPauta(pauta)).concat("/").concat(pauta.getId().toString()),
						getBody(idAssociado));
			} catch (UnknownHostException e) {
				throw new ComponenteException(e.getMessage(), e.getCause());
			}
		}).collect(Collectors.toList());
	}

	private String getPathPauta(Pauta pauta) {
		if (pauta.isSessaoAberta() && pauta.getFimVotacao().isAfter(LocalDateTime.now(getZoneId()))) {
			return Paths.PATH_FORM_VOTAR;
		} else if (pauta.isSessaoAberta() && pauta.getFimVotacao().isBefore(LocalDateTime.now(getZoneId()))) {
			return Paths.PATH_RESULTADO_VOTACAO;
		} else {
			return Paths.PATH_FORM_PAUTA_SESSAO;
		}
	}
}