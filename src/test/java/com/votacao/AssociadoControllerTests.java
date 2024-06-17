package com.votacao;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.votacao.components.TipoEnum;
import com.votacao.domain.associado.Associado;
import com.votacao.domain.associado.AssociadoRepository;
import com.votacao.service.UrlDominio;

@SpringBootTest
@AutoConfigureMockMvc
class AssociadoControllerTests {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean 
	private UrlDominio urlDominio;
	
	@MockBean 
	private AssociadoRepository associadoRepository;

	@Test
	void testListarAssociados() throws Exception {
		Mockito.when(urlDominio.montarUrl(Mockito.anyString())).thenReturn("/teste");
		List<Associado> associados = new ArrayList<Associado>();
		associados.add(new Associado(1L, "Pedro", "11122233312"));
		associados.add(new Associado(2L, "João", "33322233312"));
		Mockito.when(associadoRepository.findAll()).thenReturn(associados);
		ResultActions result = mockMvc.perform(post("/associados/listar-associados").contentType(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk())
			  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
			  .andExpect(jsonPath("$").exists())
			  .andExpect(jsonPath("$.tipo").value(TipoEnum.SELECAO.name()))
			  .andExpect(jsonPath("$.titulo").value("Lista de associados"))
			  .andExpect(jsonPath("$.itens[0].texto").value("Pedro"))
			  .andExpect(jsonPath("$.itens[0].url").value("/teste"))
			  .andExpect(jsonPath("$.itens[0].body.idAssociado").value(1))
			  .andExpect(jsonPath("$.itens[1].texto").value("João"))
			  .andExpect(jsonPath("$.itens[1].url").value("/teste"))
			  .andExpect(jsonPath("$.itens[1].body.idAssociado").value(2));
	}
		
	@Test
	void testOpcoesAssociado() throws Exception {
		Mockito.when(urlDominio.montarUrl(Mockito.anyString())).thenReturn("/teste");
		Mockito.when(associadoRepository.findById(Mockito.any())).thenReturn(Optional.of(new Associado(1L, "Pedro", "11122233312")));
		
		String associado = "{\"idAssociado\":1}";
		ResultActions result = mockMvc.perform(post("/associados/opcoes").contentType(MediaType.APPLICATION_JSON).content(associado));
		
		result.andExpect(status().isOk())
			  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
			  .andExpect(jsonPath("$").exists())
			  .andExpect(jsonPath("$.tipo").value(TipoEnum.SELECAO.name()))
			  .andExpect(jsonPath("$.titulo").value("Opções do associado Pedro"))
			  .andExpect(jsonPath("$.itens[0].texto").value("Selecionar pauta"))
			  .andExpect(jsonPath("$.itens[0].url").value("/teste"))
			  .andExpect(jsonPath("$.itens[0].body.idAssociado").value(1))
			  .andExpect(jsonPath("$.itens[1].texto").value("Cadastrar pauta"))
			  .andExpect(jsonPath("$.itens[1].url").value("/teste"))
			  .andExpect(jsonPath("$.itens[1].body.idAssociado").value(1));
	}
	
	@Test
	void testListarAssociadosException() throws Exception {
		Mockito.when(urlDominio.montarUrl(Mockito.anyString())).thenThrow(new UnknownHostException("Não foi possível montar a url"));
		List<Associado> associados = new ArrayList<Associado>();
		associados.add(new Associado(1L, "Pedro", "11122233312"));
		associados.add(new Associado(2L, "João", "33322233312"));
		Mockito.when(associadoRepository.findAll()).thenReturn(associados);
		ResultActions result = mockMvc.perform(post("/associados/listar-associados").contentType(MediaType.APPLICATION_JSON));
		result.andExpect(status().isInternalServerError())
		.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
		.andExpect(content().string("Não foi possível listar os associados"));
	}
	
	@Test
	void testOpcoesAssociadosComponenteException() throws Exception {
		Mockito.when(urlDominio.montarUrl(Mockito.anyString())).thenThrow(new UnknownHostException("Não foi possível montar a url"));
		Mockito.when(associadoRepository.findById(Mockito.any())).thenReturn(Optional.of(new Associado(1L, "Pedro", "11122233312")));
		
		String associado = "{\"idAssociado\":1}";
		ResultActions result = mockMvc.perform(post("/associados/opcoes").contentType(MediaType.APPLICATION_JSON).content(associado));
		result.andExpect(status().isInternalServerError())
		.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
		.andExpect(content().string("Não foi possível apresentar as opções"));
	}
	
	@Test
	void testOpcoesAssociadosAssociadoException() throws Exception {
		Mockito.when(associadoRepository.findById(Mockito.any())).thenThrow(new NoSuchElementException());
		
		String associado = "{\"idAssociado\":1}";
		ResultActions result = mockMvc.perform(post("/associados/opcoes").contentType(MediaType.APPLICATION_JSON).content(associado));
		result.andExpect(status().isBadRequest())
		.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
		.andExpect(content().string("Associado não encontrado"));
	}
	

}
