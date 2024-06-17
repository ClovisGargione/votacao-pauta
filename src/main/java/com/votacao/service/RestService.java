package com.votacao.service;

import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.votacao.domain.associado.dto.AssociadoResponse;
import com.votacao.domain.votacao.VotacaoException;

@Service
public class RestService {

	public AssociadoResponse consultarStatusAssociado(String cpf) throws VotacaoException {
		RestTemplate restTemplate = new RestTemplate();
		String endpoint = "https://user-info.herokuapp.com/users/".concat(cpf);
		RequestEntity<Object> request = new RequestEntity<>(HttpMethod.GET, URI.create(endpoint));
		ResponseEntity<AssociadoResponse> response = restTemplate.exchange(request, AssociadoResponse.class);
		if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
		throw new VotacaoException("Não foi possível consulta o status do CPF do associado");
	}
}
