package br.com.mentoring.controller;

import br.com.mentoring.api.UsuariosApi;
import br.com.mentoring.model.PerfilUsuario;
import br.com.mentoring.model.Usuario;
import br.com.mentoring.service.UsuarioService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@Introspected(classes = {Usuario.class, PerfilUsuario.class, String.class})
public class UsuariosController implements UsuariosApi {

    private final UsuarioService usuarioService;

    @Inject
    public UsuariosController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public Single<HttpResponse<Usuario>> carregarUsuario(@NotNull @Valid String xUsuarioEmail) {
        return Single.just(HttpResponse.ok(usuarioService.carregarUsuario(xUsuarioEmail)));
    }

    @Override
    public Single<HttpResponse<Void>> salvarUsuario(@Valid Usuario body) {
        usuarioService.salvarUsuario(body);
        return Single.just(HttpResponse.ok());
    }

    @Override
    public Single<HttpResponse<Void>> atualizarUsuario(@Valid Usuario body) {
        usuarioService.atualizarUsuario(body);
        return Single.just(HttpResponse.ok());
    }

    @Override
    public Single<HttpResponse<Void>> excluirUsuario(@NotNull @Valid String xUsuarioEmail) {
        usuarioService.excluirUsuario(xUsuarioEmail);
        return Single.just(HttpResponse.ok());
    }
}
