package br.com.mentoring.service;

import br.com.mentoring.model.Usuario;

import java.util.List;

public interface UsuarioService {

    void salvarUsuario(Usuario usuario);

    void atualizarUsuario(Usuario usuario);

    Usuario carregarUsuario(String email);

    void excluirUsuario(String email);

    List<Usuario> listarUsuarios(String filtro);

    boolean autenticarUsuario(String email, String senha);

    void esqueciMinhaSenha(String email);
}
