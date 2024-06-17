package com.votacao.service;

import java.time.ZoneId;
import java.util.Map;

public interface IService {

	Map<String, Object> getBody(Long id);
	
	ZoneId getZoneId();
}
