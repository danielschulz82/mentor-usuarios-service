package br.com.mentoring.repository.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@DynamoDBDocument
public class OpcaoRespostaEntity {

    @DynamoDBAttribute(attributeName="idOpcao")
    private String idOpcao;

    @DynamoDBAttribute(attributeName="texto")
    private String texto;

    public OpcaoRespostaEntity(String idOpcao, String texto) {
        this.idOpcao = idOpcao;
        this.texto = texto;
    }
}
