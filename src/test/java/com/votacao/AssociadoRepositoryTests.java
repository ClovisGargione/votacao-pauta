package com.votacao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.votacao.domain.associado.Associado;
import com.votacao.domain.associado.AssociadoRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AssociadoRepositoryTests {

	@Autowired
	private AssociadoRepository associadoRepository;
	
	@Test
	@Sql("/data.sql")
    public void findAllTest() {
		
		List<Associado> associados = associadoRepository.findAll();
		
		assertThat(associados).isNotEmpty();
	}
	
	@Test
	@Sql("/data.sql")
    public void findByIdTest() {
		
		Associado associado = associadoRepository.findById(1L).orElse(null);
		
		assertThat(associado).isNotNull();
	}
	
	@Test
	@Sql("/data.sql")
    public void findByIdNullTest() {
		
		Associado associado = associadoRepository.findById(0L).orElse(null);
		
		assertThat(associado).isNull();
	}
	
	@Test
	@Sql("/data.sql")
    public void countTest() {
		
		long count = associadoRepository.count();
		
		assertThat(3).isEqualTo(count);
	}
	
	@Test
	@Sql("/data.sql")
    public void existsTest() {
		
		boolean exists = associadoRepository.existsById(1L);
		
		assertThat(exists).isTrue();
	}
	
	@Test
	@Sql("/data.sql")
    public void notExistsTest() {
		
		boolean exists = associadoRepository.existsById(0L);
		
		assertThat(exists).isFalse();
	}
	
	@Test
	@Sql("/data.sql")
    public void saveTest() {
		
		Associado associado = associadoRepository.save(new Associado("Silvio", "11144477788"));
		
		assertThat(associado).isNotNull();
		assertThat(associado.getId()).isNotNull();
	}
	
	@Test
	@Sql("/data.sql")
    public void updateTest() {
		
		Associado associado = associadoRepository.save(new Associado("Silvio", "11144477788"));
		Long id = associado.getId();
		associado.setNome("Sebastião");
		associado = associadoRepository.save(associado);
		assertThat(associado).isNotNull();
		assertThat(associado.getId()).isEqualTo(id);
		assertThat(associado.getNome()).isEqualTo("Sebastião");
	}
	
	@Test
	@Sql("/data.sql")
	public void deleteTest() {
		Associado associado = associadoRepository.save(new Associado("Silvio", "11144477788"));
		Long id = associado.getId();
		associadoRepository.delete(associado);
		boolean exists = associadoRepository.existsById(id);
		assertThat(exists).isFalse();
	}
}
