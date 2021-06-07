package br.com.mentoring.repository;

import br.com.mentoring.model.*;
import br.com.mentoring.repository.entity.*;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ModelToEntityMapper {

    public static UsuarioEntity toEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setEmail(usuario.getEmail());
        entity.setTelefone(usuario.getTelefone());
        entity.setNome(usuario.getNome());
        entity.setSenha(usuario.getSenha());
        entity.setPerfilUsuario(PerfilUsuarioEntity.valueOf(usuario.getPerfilUsuario().name()));
        return entity;
    }

    public static Usuario toModel(UsuarioEntity entity) {
        Usuario usuario = new Usuario();
        usuario.setNome(entity.getNome());
        usuario.setSenha("");
        usuario.setEmail(entity.getEmail());
        usuario.setTelefone(entity.getTelefone());
        usuario.setPerfilUsuario(PerfilUsuario.fromValue(entity.getPerfilUsuario().name()));
        return usuario;
    }

    public static TemplatePesquisaEntity toEntity(TemplatePesquisa templatePesquisa) {
        TemplatePesquisaEntity entity = new TemplatePesquisaEntity();
        entity.setIdTemplate(templatePesquisa.getIdTemplate());
        entity.setDescricao(templatePesquisa.getDescricao());
        return entity;
    }

    public static TemplatePesquisa toModel(TemplatePesquisaEntity entity) {
        TemplatePesquisa templatePesquisa = new TemplatePesquisa();
        templatePesquisa.setIdTemplate(entity.getIdTemplate());
        templatePesquisa.setDescricao(entity.getDescricao());
        return templatePesquisa;
    }

    public static PerguntaEntity toEntity(Pergunta pergunta) {
        PerguntaEntity entity = new PerguntaEntity();
        entity.setIdPergunta(pergunta.getId());
        entity.setIdTemplate(pergunta.getIdTemplate());
        entity.setEnunciado(pergunta.getEnunciado());

        if(pergunta.getOpcoesResposta() != null && !pergunta.getOpcoesResposta().isEmpty()) {
            entity.setOpcoesResposta(new ArrayList<>());
            pergunta.getOpcoesResposta().forEach(opcao -> {
                entity.getOpcoesResposta().add(new OpcaoRespostaEntity(opcao.getIdOpcao(), opcao.getTexto()));
            });
        }

        return entity;
    }

    public static Pergunta toModel(PerguntaEntity entity) {
        Pergunta pergunta = new Pergunta();
        pergunta.setId(entity.getIdPergunta());
        pergunta.setIdTemplate(entity.getIdTemplate());
        pergunta.setEnunciado(entity.getEnunciado());
        entity.getOpcoesResposta().forEach(opcao -> {
            OpcaoResposta opResp = new OpcaoResposta();
            opResp.setIdOpcao(opcao.getIdOpcao());
            opResp.setTexto(opcao.getTexto());
            pergunta.addOpcoesRespostaItem(opResp);
        });
        return pergunta;
    }

    public static PesquisaEntity toEntity(Pesquisa pesquisa) {
        PesquisaEntity entity = new PesquisaEntity(pesquisa.getIdPesquisa());
        entity.setRepostas(new HashMap<>());
        entity.setIdTemplate(pesquisa.getIdTemplate());
        entity.setIdUsuario(pesquisa.getIdUsuario());
        pesquisa.getRespostas().forEach(resposta -> {
            entity.getRepostas().put(resposta.getIdPergunta(), resposta.getIdOpcaoResposta());
        });
        return entity;
    }

    public static Pesquisa toModel(PesquisaEntity entity) {
        Pesquisa pesquisa = new Pesquisa();
        pesquisa.setIdPesquisa(entity.getIdPesquisa());
        pesquisa.setIdTemplate(entity.getIdTemplate());
        pesquisa.setIdUsuario(pesquisa.getIdUsuario());
        entity.getRepostas().forEach((idPergunta, idOpcaoResposta) -> {
            Resposta resposta = new Resposta();
            resposta.setIdPergunta(idPergunta);
            resposta.setIdOpcaoResposta(idOpcaoResposta);
            pesquisa.addRespostasItem(resposta);
        });
        return pesquisa;
    }
}
