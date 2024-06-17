package com.votacao.domain.associado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

}
