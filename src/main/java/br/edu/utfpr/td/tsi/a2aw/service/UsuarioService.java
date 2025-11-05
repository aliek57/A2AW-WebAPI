package br.edu.utfpr.td.tsi.a2aw.service;

import java.util.List;

import br.edu.utfpr.td.tsi.a2aw.model.Usuario;

public interface UsuarioService {

	public void cadastrar(Usuario usuario);

	public void atualizar(Usuario usuario);

	public void remover(String idUsuario);

	public Usuario localizar(String idUsuario);

	public List<Usuario> listarTodos();

}
