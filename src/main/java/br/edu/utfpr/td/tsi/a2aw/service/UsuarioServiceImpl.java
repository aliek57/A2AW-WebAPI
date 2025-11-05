package br.edu.utfpr.td.tsi.a2aw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.a2aw.model.Usuario;
import br.edu.utfpr.td.tsi.a2aw.repository.UsuarioRepository;

@Component
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void cadastrar(Usuario usuario) {
		if (usuario.getIdade() < 18) {
			throw new RuntimeException("Usuário menor de idade");
		}
		usuario.setId(UUID.randomUUID().toString());
		usuarioRepository.salvar(usuario);
	}

	@Override
	public Usuario localizar(String idUsuario) {
		Optional<Usuario> usuario = usuarioRepository.buscarPorId(idUsuario);
		if (usuario.isPresent()) {
			return usuario.get();
		}
		return null;
	}

	@Override
	public List<Usuario> listarTodos() {
		return usuarioRepository.buscarTodos();
	}

	@Override
	public void atualizar(Usuario usuario) {
		if (usuarioRepository.buscarPorId(usuario.getId()).isEmpty()) {
            throw new RuntimeException("Usuário não encontrado para atualização.");
        }
		usuarioRepository.salvar(usuario);
	}

	@Override
	public void remover(String idUsuario) {
		if (usuarioRepository.buscarPorId(idUsuario).isEmpty()) {
            throw new RuntimeException("Usuário não encontrado para remoção.");
        }
        usuarioRepository.remover(idUsuario);
	}
}
