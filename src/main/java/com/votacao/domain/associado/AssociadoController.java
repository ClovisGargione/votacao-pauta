package com.votacao.domain.associado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.votacao.components.ComponenteBase;
import com.votacao.components.ComponenteException;
import com.votacao.domain.associado.dto.AssociadoDto;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Associado")
@RestController
@RequestMapping("/associados")
public class AssociadoController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AssociadoController.class);
		
	@Autowired
	private AssociadoService associadoService;
	
	@PostMapping("/listar-associados")
	public ResponseEntity<?> listarAssociados(){
		ComponenteBase listaDeAssociados = null;
		try {
			listaDeAssociados = associadoService.montarListaAssociados();
		} catch (ComponenteException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().body("Não foi possível listar os associados");
		}
		return ResponseEntity.ok(listaDeAssociados);
	}
	
	@PostMapping("/opcoes")
	public ResponseEntity<?> opcoesAssociado(@RequestBody AssociadoDto associadoDto) {
		ComponenteBase componenteBase = null;
		try {
			componenteBase = associadoService.montarOpcoes(associadoDto);
		} catch (ComponenteException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().body("Não foi possível apresentar as opções");
		} catch (AssociadoException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(componenteBase);
	}
}
