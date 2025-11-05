package br.edu.utfpr.td.tsi.a2aw.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import br.edu.utfpr.td.tsi.a2aw.model.Usuario;

@Repository
public class UsuarioRepository {
	private Map<String, Usuario> bd = new ConcurrentHashMap<>();

	public Usuario salvar(Usuario usuario) {
        if (usuario.getId() == null || usuario.getId().isEmpty()) {
            throw new RuntimeException("ID do usuário não pode ser nulo para salvar.");
        }
        bd.put(usuario.getId(), usuario);
        return usuario;
    }
	
	public Optional<Usuario> buscarPorId(String id) {
        return Optional.ofNullable(bd.get(id));
    }
	
	public List<Usuario> buscarTodos() {
        return new ArrayList<>(bd.values());
    }
	
	public void remover(String id) {
        bd.remove(id);
    }
}
