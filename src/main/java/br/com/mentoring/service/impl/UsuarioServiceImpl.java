package br.com.mentoring.service.impl;

import br.com.mentoring.dependency.PasswordUtils;
import br.com.mentoring.exception.TipoErro;
import br.com.mentoring.exception.UsuarioServiceException;
import br.com.mentoring.model.Usuario;
import br.com.mentoring.repository.ModelToEntityMapper;
import br.com.mentoring.repository.UsuarioRepository;
import br.com.mentoring.repository.entity.UsuarioEntity;
import br.com.mentoring.service.UsuarioService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Inject
    private UsuarioRepository usuarioRepository;

    @Inject
    private PasswordUtils passwordUtils;

    @Override
    public void salvarUsuario(Usuario usuario) {
        LOGGER.debug("Salvando novo usuário...");
        UsuarioEntity entity = ModelToEntityMapper.toEntity(usuario);
        entity.setSenha(passwordUtils.generateSecurePassword(
                entity.getSenha(), passwordUtils.getSalt()));
        usuarioRepository.salvar(entity);
    }

    @Override
    @SneakyThrows
    public void atualizarUsuario(Usuario usuario) {
        LOGGER.debug("Atualizando dados do usuário...");
        Optional<UsuarioEntity> optUsuario = usuarioRepository.carregar(usuario.getEmail());
        if(optUsuario.isEmpty()) {
            LOGGER.warn("Usuário não encontrado para atualização. Id: {}", usuario.getEmail());
            throw new UsuarioServiceException(TipoErro.MUS01_USUARIO_NAO_ENCONTRADO);
        }

        UsuarioEntity entidade = optUsuario.get();
        entidade.setNome(usuario.getNome());
        entidade.setTelefone(usuario.getTelefone());
        usuarioRepository.atualizar(entidade);
    }

    @Override
    @SneakyThrows
    public Usuario carregarUsuario(String email) {
        LOGGER.debug("Carregando dados do usuário...");
        Optional<UsuarioEntity> optUsuario = usuarioRepository.carregar(email);
        if(optUsuario.isEmpty()) {
            LOGGER.warn("Usuário não encontrado. Id: {}", email);
            throw new UsuarioServiceException(TipoErro.MUS01_USUARIO_NAO_ENCONTRADO);
        }

        return ModelToEntityMapper.toModel(optUsuario.get());
    }

    @Override
    @SneakyThrows
    public void excluirUsuario(String email) {
        LOGGER.debug("Excluindo dados do usuário...");
        Optional<UsuarioEntity> optUsuario = usuarioRepository.carregar(email);
        if(optUsuario.isEmpty()) {
            LOGGER.warn("Usuário não encontrado para exclusão. Id: {}", email);
            throw new UsuarioServiceException(TipoErro.MUS01_USUARIO_NAO_ENCONTRADO);
        }

        usuarioRepository.excluir(optUsuario.get());
    }

    @Override
    public List<Usuario> listarUsuarios(String filtro) {
        LOGGER.debug("Listando usuário com filtro: {}", filtro);
        List<Usuario> usuarios = new ArrayList<>();
        usuarioRepository.filtrar(filtro).forEach(item -> {
            usuarios.add(ModelToEntityMapper.toModel(item));
        });
        return usuarios;
    }

    @Override
    public boolean autenticarUsuario(String email, String senha) {
        return false;
    }

    @Override
    public void esqueciMinhaSenha(String email) {

    }
}
