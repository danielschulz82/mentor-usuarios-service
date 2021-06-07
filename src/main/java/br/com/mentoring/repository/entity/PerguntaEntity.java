package br.com.mentoring.repository.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@DynamoDBTable(tableName = "MENTOR_USUARIOS_SERVICE_PRD_PERGUNTA_PESQUISA")
public class PerguntaEntity {

    @DynamoDBHashKey(attributeName = "idPergunta")
    private String idPergunta;

    @DynamoDBAttribute(attributeName="idTemplate")
    private String idTemplate;

    @DynamoDBAttribute(attributeName="enunciado")
    private String enunciado;

    @DynamoDBAttribute(attributeName="opcoesResposta")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.L)
    private List<OpcaoRespostaEntity> opcoesResposta;

    public PerguntaEntity(String idPergunta) {
        this.opcoesResposta = new ArrayList<>();
        this.idPergunta = idPergunta;
    }
}
