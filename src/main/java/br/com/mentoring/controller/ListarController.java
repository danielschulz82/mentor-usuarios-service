package br.com.mentoring.controller;

import br.com.mentoring.api.ListarApi;
import br.com.mentoring.model.Pergunta;
import br.com.mentoring.model.Pesquisa;
import br.com.mentoring.model.TemplatePesquisa;
import br.com.mentoring.model.Usuario;
import br.com.mentoring.service.PesquisaService;
import br.com.mentoring.service.UsuarioService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.reactivex.Single;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@Controller
@Introspected(classes = {String.class})
public class ListarController implements ListarApi {

    private final UsuarioService usuarioService;

    private final PesquisaService pesquisaService;

    @Inject
    public ListarController(UsuarioService usuarioService, PesquisaService pesquisaService) {
        this.pesquisaService = pesquisaService;
        this.usuarioService = usuarioService;
    }

    @Override
    public Single<HttpResponse<List<Usuario>>> listarUsuarios(@Nullable @Valid String xUsuarioNome) {
        return Single.just(HttpResponse.ok(usuarioService.listarUsuarios(xUsuarioNome)));
    }

    @Override
    public Single<HttpResponse<List<Pergunta>>> listarPerguntas(@Nullable @Valid String xTemplateId, @Nullable @Valid String xPerguntaEnunciado) {
        return Single.just(HttpResponse.ok(pesquisaService.listaPerguntas(xTemplateId)));
    }

    @Override
    public Single<HttpResponse<List<TemplatePesquisa>>> listarTemplates(@Nullable @Valid String xTemplateDescricao) {
        return Single.just(HttpResponse.ok(pesquisaService.listaTemplatePesquisa(xTemplateDescricao)));
    }

    @Override
    public Single<HttpResponse<List<Pesquisa>>> listarResultados(@Nullable String xTemplateId) {
        return Single.just(HttpResponse.ok(pesquisaService.listaResultados(xTemplateId)));
    }
}
