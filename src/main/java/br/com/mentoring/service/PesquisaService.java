package br.com.mentoring.service;

import br.com.mentoring.model.Pergunta;
import br.com.mentoring.model.Pesquisa;
import br.com.mentoring.model.TemplatePesquisa;

import java.util.List;

public interface PesquisaService {

    void salvarTemplatePesquisa(TemplatePesquisa templatePesquisa);

    void atualizarTemplatePesquisa(TemplatePesquisa templatePesquisa);

    TemplatePesquisa carregarTemplatePesquisa(String id);

    List<TemplatePesquisa> listaTemplatePesquisa(String filtro);

    void excluirTemplatePesquisa(String id);

    void salvarPergunta(Pergunta pergunta);

    void atualizarPergunta(Pergunta pergunta);

    Pergunta carregarPergunta(String id);

    List<Pergunta> listaPerguntas(String filtro);

    void excluirPergunta(String id);

    void salvarPesquisa(Pesquisa pesquisa);

    Pesquisa carregarPesquisa(String idPesquisa);

    List<Pesquisa> listaResultados(String idTemplate);
}
