package br.edu.utfpr.td.tsi.a2aw.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.a2aw.endpoint.UsuarioEndpoint;
import jakarta.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/a2aw-api")
public class JerseyConfiguration extends ResourceConfig {

	public JerseyConfiguration() {
		this.register(UsuarioEndpoint.class);
	}
}