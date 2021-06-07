package br.com.mentoring.exception;

import lombok.Getter;

@Getter
public enum TipoErro {

    MUS01_USUARIO_NAO_ENCONTRADO("MUS01", "Usuário não encontrado."),
    MUS02_PERGUNTA_NAO_ENCONTRADA("MUS02", "Pergunta não encontrada."),
    MUS03_TEMPLATE_PESQUISA_NAO_ENCONTRADO("MUS03", "Template de pesquisa não encontrado."),
    MUS04_PESQUISA_NAO_ENCONTRADA("MUS04", "Pesquisa não encontrada."),
    MUS05_PESQUISA_POSSUI_RESPOSTAS("MUS05", "A pesquisa possui respostas. Não é possível excluí-la.");

    private String id;
    private String mensagem;

    TipoErro(String id, String mensagem) {
        this.id = id;
        this.mensagem = mensagem;
    }
}
