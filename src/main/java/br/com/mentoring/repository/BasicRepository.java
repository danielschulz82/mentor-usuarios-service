package br.com.mentoring.repository;

import java.util.List;
import java.util.Optional;

public interface BasicRepository<T> {

    void salvar(T entidade);

    void atualizar(T entidade);

    Optional<T> carregar(String id);

    List<T> filtrar(String query);

    void excluir(T entidade);
}
