package com.votacao.domain.votacao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.votacao.components.ComponenteBase;
import com.votacao.components.ComponenteException;
import com.votacao.components.ComponenteForm;
import com.votacao.domain.associado.AssociadoException;
import com.votacao.domain.associado.dto.AssociadoDto;
import com.votacao.domain.pauta.PautaException;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Votação")
@RestController
@RequestMapping("/votacao")
public class VotacaoController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VotacaoController.class);
	
	@Autowired
	private VotacaoService votacaoService;
	
	@PostMapping("/form-votar/{idPauta}")
	public ResponseEntity<?> votar(@RequestBody AssociadoDto associadoDto, @PathVariable(name = "idPauta") Long idPauta){
		ComponenteForm componenteForm = null;
		try {
			componenteForm = votacaoService.formVotacao(idPauta, associadoDto);
		} catch(ComponenteException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().body("Não foi possível abrir o form para votação");
		} catch (PautaException | AssociadoException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(componenteForm);
	}

	@PostMapping("/registrar-voto/{idPauta}/{voto}")
	public ResponseEntity<?> registrarVoto(@RequestBody AssociadoDto associadoDto, @PathVariable(name = "idPauta") Long idPauta, @PathVariable(name = "voto") String voto){
		ComponenteBase componenteBase = null;
		try {
			componenteBase = votacaoService.registrarVoto(idPauta, voto, associadoDto);
		} catch(ComponenteException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().body("Não foi possível registrar o voto");
		} catch (VotacaoException | PautaException | AssociadoException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().body(e.getMessage());
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.badRequest().body("Voto inválido");
		}
		return ResponseEntity.ok(componenteBase);
	}
	
	@PostMapping("/resultado/{idPauta}")
	public ResponseEntity<?> resultadoVotacao(@RequestBody AssociadoDto associadoDto, @PathVariable(name = "idPauta") Long idPauta) {
		ComponenteForm componenteForm = null;
		try {
			componenteForm = votacaoService.formResultado(associadoDto, idPauta);
		} catch(ComponenteException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().body("Não foi possível apresentar o resultado");
		} catch (VotacaoException | PautaException | AssociadoException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
		return ResponseEntity.ok(componenteForm);
	}
}
