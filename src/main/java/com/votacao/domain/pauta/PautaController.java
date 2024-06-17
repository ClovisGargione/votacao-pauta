package com.votacao.domain.pauta;

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
import com.votacao.domain.pauta.dto.PautaDto;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pauta")
@RestController
@RequestMapping("/pauta")
public class PautaController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PautaController.class);

	@Autowired
	private PautaService pautaService;
	
	@PostMapping("/listar-pautas")
	public ResponseEntity<?> listarPautas(@RequestBody AssociadoDto associadoDto){
		
		ComponenteBase pautas = null;
		try {
			pautas = pautaService.pautas(associadoDto);
		} catch(ComponenteException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().body("Não foi possível listar as pautas");
		} catch (PautaException | AssociadoException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(pautas);
	}
	
	@PostMapping("/form-pauta")
	public ResponseEntity<?> formPautas(@RequestBody AssociadoDto associadoDto){
		ComponenteForm componenteForm = null;
		try {
			componenteForm = pautaService.montarFormPauta(associadoDto);
		} catch(ComponenteException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().body("Não foi possível abrir o form de cadastro");
		} catch (AssociadoException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(componenteForm);
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrar(@RequestBody PautaDto pautaDto){
		ComponenteBase pautas = null;
		try {
			pautas = pautaService.cadastarPauta(pautaDto);
		} catch (PautaException | AssociadoException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().body(e.getMessage());
		} catch (ComponenteException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().body("Não foi possível listar a pautas");
		}
		return ResponseEntity.ok(pautas);
	}
	
	@PostMapping("/form-sessao/{idPauta}")
	public ResponseEntity<?> formSessao(@RequestBody AssociadoDto associadoDto, @PathVariable(name = "idPauta") Long idPauta) {
		ComponenteForm formSessao = null;
		try {
			formSessao = pautaService.formSessao(idPauta, associadoDto);
		} catch (ComponenteException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().body("Não foi possível abrir o form de sessão");
		} catch (PautaException | AssociadoException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(formSessao);
	}
	
	@PostMapping("/abrir-sessao/{idPauta}")
	public ResponseEntity<?> abrirSessao(@RequestBody AssociadoDto associadoDto, @PathVariable(name = "idPauta") Long idPauta){
		ComponenteBase listaDePautas = null;
		try {
			listaDePautas = pautaService.abrirSessao(idPauta, associadoDto);
		} catch (ComponenteException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().body("Não foi possível abrir a sessão de votação");
		} catch (PautaException | AssociadoException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.ok(listaDePautas);
	}
	
}
