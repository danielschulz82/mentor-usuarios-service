package br.com.mentoring.repository.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@DynamoDBTable(tableName = "MENTOR_USUARIOS_SERVICE_PRD_TEMPLATE_PESQUISA")
public class TemplatePesquisaEntity {

    @DynamoDBHashKey(attributeName = "idTemplate")
    private String idTemplate;

    @DynamoDBAttribute(attributeName="descricao")
    private String descricao;

    public TemplatePesquisaEntity(String idTemplate) {
        this.idTemplate = idTemplate;
    }
}
