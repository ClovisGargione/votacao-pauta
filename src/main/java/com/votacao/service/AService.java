package com.votacao.service;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public abstract class AService implements IService {

	@Override
	public Map<String, Object> getBody(Long id) {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("idAssociado", id);
		return body;
	}

	@Override
	public ZoneId getZoneId() {
		return ZoneId.of("America/Sao_Paulo");
	}
}
