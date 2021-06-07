package br.com.mentoring.exception;

import lombok.Getter;

@Getter
public class UsuarioServiceException extends Exception {

    private String id;

    private String mensagem;

    public UsuarioServiceException(TipoErro tipoErro) {
        super(tipoErro.getMensagem());
        this.id = tipoErro.getId();
        this.mensagem = tipoErro.getMensagem();
    }
}
