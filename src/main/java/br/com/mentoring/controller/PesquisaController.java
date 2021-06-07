package br.com.mentoring.controller;

import br.com.mentoring.api.PesquisaApi;
import br.com.mentoring.model.OpcaoResposta;
import br.com.mentoring.model.Pergunta;
import br.com.mentoring.model.Pesquisa;
import br.com.mentoring.model.TemplatePesquisa;
import br.com.mentoring.service.PesquisaService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@Introspected(classes = {TemplatePesquisa.class, Pergunta.class, OpcaoResposta.class, Pesquisa.class, String.class})
public class PesquisaController implements PesquisaApi {

    private final PesquisaService pesquisaService;

    @Inject
    public PesquisaController(PesquisaService pesquisaService) {
        this.pesquisaService = pesquisaService;
    }

    @Override
    public Single<HttpResponse<Void>> atualizarTemplate(@Valid TemplatePesquisa body) {
        pesquisaService.atualizarTemplatePesquisa(body);
        return Single.just(HttpResponse.ok());
    }

    @Override
    public Single<HttpResponse<TemplatePesquisa>> carregarTemplate(@NotNull @Valid String xTemplateId) {
        return Single.just(HttpResponse.ok(pesquisaService.carregarTemplatePesquisa(xTemplateId)));
    }

    @Override
    public Single<HttpResponse<Void>> excluirTemplate(@NotNull @Valid String xTemplateId) {
        pesquisaService.excluirTemplatePesquisa(xTemplateId);
        return Single.just(HttpResponse.ok());
    }

    @Override
    public Single<HttpResponse<Void>> salvarTemplate(@Valid TemplatePesquisa body) {
        pesquisaService.salvarTemplatePesquisa(body);
        return Single.just(HttpResponse.ok());
    }

    @Override
    public Single<HttpResponse<Void>> atualizarPergunta(@Valid Pergunta body) {
        pesquisaService.atualizarPergunta(body);
        return Single.just(HttpResponse.ok());
    }

    @Override
    public Single<HttpResponse<Pergunta>> carregarPergunta(@NotNull @Valid String xPerguntaId) {
        return Single.just(HttpResponse.ok(pesquisaService.carregarPergunta(xPerguntaId)));
    }

    @Override
    public Single<HttpResponse<Void>> excluirPergunta(@NotNull @Valid String xPerguntaId) {
        pesquisaService.excluirPergunta(xPerguntaId);
        return Single.just(HttpResponse.ok());
    }

    @Override
    public Single<HttpResponse<Void>> salvarPergunta(@Valid Pergunta body) {
        pesquisaService.salvarPergunta(body);
        return Single.just(HttpResponse.ok());
    }

    @Override
    public Single<HttpResponse<Pesquisa>> carregarPesquisa(@NotNull @Valid String xPesquisaId) {
        return Single.just(HttpResponse.ok(pesquisaService.carregarPesquisa(xPesquisaId)));
    }

    @Override
    public Single<HttpResponse<Void>> salvarPesquisa(@Valid Pesquisa body) {
        pesquisaService.salvarPesquisa(body);
        return Single.just(HttpResponse.ok());
    }
}
