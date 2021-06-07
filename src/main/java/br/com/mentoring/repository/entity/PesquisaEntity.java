package br.com.mentoring.repository.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@DynamoDBTable(tableName = "MENTOR_USUARIOS_SERVICE_PRD_RESPOSTAS_PESQUISA")
public class PesquisaEntity {

    @DynamoDBHashKey(attributeName = "idPesquisa")
    private String idPesquisa;

    @DynamoDBAttribute(attributeName="idTemplate")
    private String idTemplate;

    @DynamoDBAttribute(attributeName="idUsuario")
    private String idUsuario;

    // chave=idPergunta | valor=idOpcaoResposta
    @DynamoDBAttribute(attributeName="repostas")
    private Map<String, String> repostas;

    public PesquisaEntity(String idPesquisa) {
        this.idPesquisa = idPesquisa;
    }
}
