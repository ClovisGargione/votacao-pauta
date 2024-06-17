package com.votacao.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Nullable;

@Component
@Validated
@ConfigurationProperties("dominio")
public class DominioProperties {

	@Nullable
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

