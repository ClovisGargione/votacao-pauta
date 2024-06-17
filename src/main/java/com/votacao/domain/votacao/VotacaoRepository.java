package com.votacao.domain.votacao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.votacao.domain.associado.Associado;
import com.votacao.domain.pauta.Pauta;


@RepositoryRestResource(exported = false)
public interface VotacaoRepository extends JpaRepository<Votacao, Long> {

	Optional<Votacao> findByAssociadoAndPauta(@Param(value = "associado") Associado associado, @Param(value = "pauta") Pauta pauta);
	List<Votacao> findByPauta(@Param(value = "pauta") Pauta pauta); 
}
