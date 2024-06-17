package com.votacao.service;

import static java.util.Objects.isNull;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.stereotype.Component;

import com.votacao.configuration.DominioProperties;

@Component
public class UrlDominio {
	
	private static final String HTTP = "http://";

	@Autowired
	private ServletWebServerApplicationContext webServerAppCtxt;
	
	@Value("${server.servlet.context-path}")
	private String contextPath;
	
	@Autowired
	private DominioProperties dominio;
	
	public String montarUrl(String path) throws UnknownHostException {
		if(isNull(path)) {
			path = "";
		}
		
		return getDominio().concat(path);
	}
	
	private String getDominio() throws UnknownHostException {
		if(isNull(dominio.getUrl()) || dominio.getUrl().isBlank()) {
			InetAddress ip = InetAddress.getLocalHost();
			return HTTP.concat(ip.getHostAddress()).concat(":").concat(String.valueOf(webServerAppCtxt.getWebServer().getPort())).concat(contextPath);
		}
		return dominio.getUrl().contains(HTTP) ? dominio.getUrl().concat(contextPath) : HTTP.concat(dominio.getUrl()).concat(contextPath);
	}
}
