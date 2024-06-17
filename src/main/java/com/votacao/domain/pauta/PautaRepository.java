package com.votacao.domain.pauta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface PautaRepository extends JpaRepository<Pauta, Long> {

}
