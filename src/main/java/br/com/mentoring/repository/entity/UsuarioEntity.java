package br.com.mentoring.repository.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@DynamoDBTable(tableName = "MENTOR_USUARIOS_SERVICE_PRD_REPOSITORIO_USUARIO")
public class UsuarioEntity {

    @DynamoDBHashKey(attributeName = "email")
    private String email;

    @DynamoDBAttribute(attributeName="senha")
    private String senha;

    @DynamoDBAttribute(attributeName="nome")
    private String nome;

    @DynamoDBAttribute(attributeName="telefone")
    private String telefone;

    @DynamoDBAttribute(attributeName="valido")
    private boolean valido;

    @DynamoDBAttribute(attributeName="trocarSenha")
    private boolean trocarSenha;

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName="perfilUsuario")
    private PerfilUsuarioEntity perfilUsuario;

    public UsuarioEntity(String email) {
        this.email = email;
    }
}
