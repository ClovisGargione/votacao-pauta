package com.votacao.domain.votacao;

import static java.util.Objects.nonNull;

import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacao.components.Botao;
import com.votacao.components.ComponenteBase;
import com.votacao.components.ComponenteException;
import com.votacao.components.ComponenteForm;
import com.votacao.components.TipoEnum;
import com.votacao.domain.associado.Associado;
import com.votacao.domain.associado.AssociadoException;
import com.votacao.domain.associado.AssociadoService;
import com.votacao.domain.associado.dto.AssociadoDto;
import com.votacao.domain.pauta.Pauta;
import com.votacao.domain.pauta.PautaException;
import com.votacao.domain.pauta.PautaService;
import com.votacao.service.AVotacaoService;
import com.votacao.service.Paths;
import com.votacao.service.UrlDominio;

@Service
public class VotacaoService extends AVotacaoService {

	@Autowired
	private VotacaoRepository votacaoRepository;
	
	@Autowired
	private ResultadoVotacaoRepository resultadoVotacaoRepository;

	@Autowired
	private PautaService pautaService;

	@Autowired
	private AssociadoService associadoService;
	
	/*@Autowired
	private RestService restService;*/

	@Autowired
	private UrlDominio urlDominio;

	public ComponenteForm formVotacao(Long idPauta, AssociadoDto associadoDto) throws ComponenteException, PautaException, AssociadoException {
		Pauta pauta = pautaService.getPauta(idPauta);
		Associado associado = associadoService.getAssociado(associadoDto.getIdAssociado());
		ComponenteForm componenteForm = new ComponenteForm();
		componenteForm.setTipo(TipoEnum.FORMULARIO);
		componenteForm.setTitulo("Votação");
		componenteForm.setItens(montarItens(pauta));
		try {
			componenteForm.setBotaoOk(new Botao("Sim", urlDominio.montarUrl(Paths.PATH_REGISTRAR_VOTO).concat("/")
					.concat(pauta.getId().toString()).concat("/").concat(VotacaoEnum.SIM.name()), getBody(associado.getId())));
			componenteForm.setBotaoCancelar(new Botao("Não", urlDominio.montarUrl(Paths.PATH_REGISTRAR_VOTO).concat("/")
					.concat(pauta.getId().toString()).concat("/").concat(VotacaoEnum.NAO.name()), getBody(associado.getId())));
		} catch (UnknownHostException e) {
			throw new ComponenteException(e.getMessage(), e.getCause());
		}

		return componenteForm;
	}

	public ComponenteBase registrarVoto(Long idPauta, String voto, AssociadoDto associadoDto) throws VotacaoException, IllegalArgumentException, ComponenteException, PautaException, AssociadoException {
		Pauta pauta = pautaService.getPauta(idPauta);
		Associado associado = associadoService.getAssociado(associadoDto.getIdAssociado());
		Votacao votacao = votacaoRepository.findByAssociadoAndPauta(associado, pauta).orElse(null);
		validacoes(associado.getCpf(), votacao, pauta);
		VotacaoEnum votoEnum = VotacaoEnum.valueOf(voto);
		votacaoRepository.save(new Votacao(associado, pauta, votoEnum.equals(VotacaoEnum.SIM)));
		return pautaService.pautas(new AssociadoDto(associado.getId()));
	}

	private void validacoes(String cpf, Votacao votacao, Pauta pauta) throws VotacaoException {
		if (nonNull(votacao)) {
			throw new VotacaoException("O associado já votou");
		}
		if(!pauta.isSessaoAberta()) {
			throw new VotacaoException("Sessão não iniciada");
		}
		if(pauta.getFimVotacao().isBefore(LocalDateTime.now(getZoneId()))) {
			throw new VotacaoException("A votação já encerrou");
		}
		/* API de consulta não está funcionando
		AssociadoResponse associadoResponse = restService.consultarStatusAssociado(cpf);
		VotacaoEnum votacaoEnum = VotacaoEnum.valueOf(associadoResponse.getStatus());
		if(votacaoEnum.equals(VotacaoEnum.UNABLE_TO_VOTE)) {
			throw new VotacaoException("O associado não está liberado para votar");
		}*/
	}

	public ComponenteForm formResultado(AssociadoDto associadoDto, Long idPauta) throws VotacaoException, PautaException, AssociadoException {
		Pauta pauta = pautaService.getPauta(idPauta);
		Associado associado = associadoService.getAssociado(associadoDto.getIdAssociado());
		List<Votacao> votos = votacaoRepository.findByPauta(pauta); 
		ResultadoVotacao resultadoVotacao = apurarVotos(votos, pauta);
		ComponenteForm componenteForm = new ComponenteForm();
		componenteForm.setTipo(TipoEnum.FORMULARIO);
		componenteForm.setTitulo("Resultado da votação");		
		componenteForm.setItens(montarItens(resultadoVotacao));
		try {
			componenteForm.setBotaoOk(new Botao("Voltar", urlDominio.montarUrl(Paths.PATH_PAUTAS), getBody(associado.getId())));
		} catch (UnknownHostException e) {
			throw new ComponenteException(e.getMessage(), e.getCause());
		}
		return componenteForm;
	}

	private ResultadoVotacao apurarVotos(List<Votacao> votos, Pauta pauta) throws VotacaoException {
		if(!pauta.isSessaoAberta()) {
			throw new VotacaoException("Sessão não iniciada");
		}
		ResultadoVotacao resultado = resultadoVotacaoRepository.findByPauta(pauta).orElse(new ResultadoVotacao());
		Long sim = votos.stream().filter(voto -> voto.isVoto()).count();
		Long nao = votos.stream().filter(voto -> !voto.isVoto()).count();
		resultado.setPauta(pauta);
		resultado.setSim(sim.intValue());
		resultado.setNao(nao.intValue());
		resultado = resultadoVotacaoRepository.save(resultado);
		return resultado;
	}

	
	
	
}
