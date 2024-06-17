package com.votacao.domain.votacao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.votacao.domain.pauta.Pauta;

@RepositoryRestResource(exported = false)
public interface ResultadoVotacaoRepository extends JpaRepository<ResultadoVotacao, Long> {

	Optional<ResultadoVotacao> findByPauta(@Param(value = "pauta") Pauta pauta); 
}
