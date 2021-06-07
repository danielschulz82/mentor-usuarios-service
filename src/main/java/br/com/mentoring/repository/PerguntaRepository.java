package br.com.mentoring.repository;

import br.com.mentoring.repository.entity.PerguntaEntity;

import java.util.List;

public interface PerguntaRepository extends BasicRepository<PerguntaEntity> {

    void excluir(List<PerguntaEntity> perguntas);
}
