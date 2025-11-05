package br.edu.utfpr.td.tsi.a2aw.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.a2aw.model.Usuario;
import br.edu.utfpr.td.tsi.a2aw.service.UsuarioService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Component
@Path("usuario")
public class UsuarioEndpoint {

	@Autowired
	private UsuarioService usuarioService;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findUsuario(@PathParam("id") String id) {
		Usuario usuario = usuarioService.localizar(id);
		if (usuario != null) {
            return Response.ok(usuario).build();
        } else {
            return Response.status(Status.NOT_FOUND).build(); 
        }
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarTodos() {
		List<Usuario> usuarios = usuarioService.listarTodos();
		return Response.ok(usuarios).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(Usuario usuario) {
		try {
            usuarioService.cadastrar(usuario);
            return Response.ok(usuario).build(); 
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST)
                           .entity(e.getMessage())
                           .build();
        }
	}
	
	@PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizar(Usuario usuario) {
        try {
            usuarioService.atualizar(usuario);
            return Response.ok(usuario).build();
        } catch (Exception e) {
            return Response.status(Status.NOT_FOUND)
                           .entity(e.getMessage())
                           .build();
        }
    }
	
	@DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") String id) {
        try {
            usuarioService.remover(id);
            return Response.noContent().build(); 
        } catch (Exception e) {
            return Response.status(Status.NOT_FOUND)
                           .entity(e.getMessage())
                           .build();
        }
    }
}
