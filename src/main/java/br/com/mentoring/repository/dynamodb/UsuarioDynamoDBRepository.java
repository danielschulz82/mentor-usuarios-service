package br.com.mentoring.repository.dynamodb;

import br.com.mentoring.repository.UsuarioRepository;
import br.com.mentoring.repository.entity.UsuarioEntity;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import io.micronaut.context.annotation.Prototype;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Prototype
public class UsuarioDynamoDBRepository implements UsuarioRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Inject
    public UsuarioDynamoDBRepository(AmazonDynamoDB amazonDynamoDB) {
        this.dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }

    @Override
    public void salvar(UsuarioEntity entidade) {
        dynamoDBMapper.save(entidade);
    }

    @Override
    public void atualizar(UsuarioEntity entidade) {
        this.salvar(entidade);
    }

    @Override
    public Optional<UsuarioEntity> carregar(String email) {
        UsuarioEntity entity = dynamoDBMapper.load(new UsuarioEntity(email));
        return entity ==  null ? Optional.empty() : Optional.of(entity);
    }

    @Override
    public List<UsuarioEntity> filtrar(String query) {
        Map<String, Condition> filter = new HashMap<String, Condition>();
        filter.put("nome", new Condition()
                .withComparisonOperator(ComparisonOperator.CONTAINS)
                .withAttributeValueList(new AttributeValue(query)));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withScanFilter(filter);
        return dynamoDBMapper.scan(UsuarioEntity.class, scanExpression);
    }

    @Override
    public void excluir(UsuarioEntity entidade) {
        dynamoDBMapper.delete(entidade);
    }
}
