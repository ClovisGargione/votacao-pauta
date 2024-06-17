package com.votacao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.votacao.components.ComponenteBase;
import com.votacao.components.ComponenteException;
import com.votacao.components.TipoEnum;
import com.votacao.domain.associado.Associado;
import com.votacao.domain.associado.AssociadoException;
import com.votacao.domain.associado.AssociadoRepository;
import com.votacao.domain.associado.AssociadoService;
import com.votacao.domain.associado.dto.AssociadoDto;
import com.votacao.service.UrlDominio;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AssociadoServiceTests {

	@MockBean
	private UrlDominio urlDominio;
	
	@Mock
	private AssociadoRepository associadoRepository;
	
	@InjectMocks
	private AssociadoService associadoService;
	
	@Test
	void montarListaAssociadosTest() throws Exception {
		Mockito.when(urlDominio.montarUrl(Mockito.anyString())).thenReturn("/teste");
		List<Associado> associados = new ArrayList<Associado>();
		associados.add(new Associado(1L, "Pedro", "11122233312"));
		associados.add(new Associado(2L, "João", "33322233312"));
		Mockito.when(associadoRepository.findAll()).thenReturn(associados);
		
		ComponenteBase componenteBase = associadoService.montarListaAssociados();
		assertThat(componenteBase).isNotNull();
		assertThat(componenteBase.getTipo()).isEqualTo(TipoEnum.SELECAO);
		assertThat(componenteBase.getTitulo()).isEqualTo("Lista de associados");
		assertThat(componenteBase.getItens()).isNotEmpty();
	}
	
	@Test
	void montarListaAssociadosComponenteExceptionTest() throws Exception {
		Mockito.when(urlDominio.montarUrl(Mockito.anyString())).thenThrow(new UnknownHostException("Não foi possível montar a url"));
		List<Associado> associados = new ArrayList<Associado>();
		associados.add(new Associado(1L, "Pedro", "11122233312"));
		associados.add(new Associado(2L, "João", "33322233312"));
		Mockito.when(associadoRepository.findAll()).thenReturn(associados);
		
		assertThatThrownBy(() -> associadoService.montarListaAssociados()).isInstanceOf(ComponenteException.class);
	}
	
	@Test
	void montarOpcoesAssociadoComponenteExceptionTest() throws Exception {
		Mockito.when(urlDominio.montarUrl(Mockito.anyString())).thenThrow(new UnknownHostException("Não foi possível montar a url"));
		Mockito.when(associadoRepository.findById(Mockito.any())).thenReturn(Optional.of(new Associado(1L, "Pedro", "11122233312")));
		new Associado(1L, "Pedro", "11122233312");
		assertThatThrownBy(() -> associadoService.montarOpcoes(new AssociadoDto(1L))).isInstanceOf(ComponenteException.class);
	}
	
	@Test
	void montarOpcoesAssociadoAssociadoExceptionTest() throws Exception {
		Mockito.when(urlDominio.montarUrl(Mockito.anyString())).thenReturn("/teste");
		Mockito.when(associadoRepository.findById(Mockito.any())).thenThrow(new NoSuchElementException());
		new Associado(1L, "Pedro", "11122233312");
		assertThatThrownBy(() -> associadoService.montarOpcoes(new AssociadoDto(1L))).isInstanceOf(AssociadoException.class);
	}
	
	@Test
	void montarOpcoesAssociadoTest() throws Exception {
		Mockito.when(urlDominio.montarUrl(Mockito.anyString())).thenReturn("/teste");
		Mockito.when(associadoRepository.findById(Mockito.any())).thenReturn(Optional.of(new Associado(1L, "Pedro", "11122233312")));
		ComponenteBase componenteBase = associadoService.montarOpcoes(new AssociadoDto(1L));
		assertThat(componenteBase).isNotNull();
		assertThat(componenteBase.getTipo()).isEqualTo(TipoEnum.SELECAO);
		assertThat(componenteBase.getTitulo()).isEqualTo("Opções do associado Pedro");
		assertThat(componenteBase.getItens()).isNotEmpty();
	}
	
	@Test
	void getAssociadoTest() throws AssociadoException {
		Mockito.when(associadoRepository.findById(Mockito.any())).thenReturn(Optional.of(new Associado(1L, "Pedro", "11122233312")));
		Associado associado = associadoService.getAssociado(1L);
		assertThat(associado).isNotNull();
		assertThat(associado.getId()).isEqualTo(1);
		assertThat(associado.getNome()).isEqualTo("Pedro");
		assertThat(associado.getCpf()).isEqualTo("11122233312");
	}
	
	@Test
	void getAssociadoAssociadoExceptionTest() {
		Mockito.when(associadoRepository.findById(Mockito.any())).thenThrow(new NoSuchElementException());
		assertThatThrownBy(() -> associadoService.getAssociado(1L)).isInstanceOf(AssociadoException.class);
	}
}
