package br.com.mentoring.repository.dynamodb;

import br.com.mentoring.repository.PerguntaRepository;
import br.com.mentoring.repository.entity.PerguntaEntity;
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
public class PerguntaDynamoDBRepository implements PerguntaRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Inject
    public PerguntaDynamoDBRepository(AmazonDynamoDB amazonDynamoDB) {
        this.dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }

    @Override
    public void salvar(PerguntaEntity entidade) {
        dynamoDBMapper.save(entidade);
    }

    @Override
    public void atualizar(PerguntaEntity entidade) {
        this.salvar(entidade);
    }

    @Override
    public Optional<PerguntaEntity> carregar(String id) {
        PerguntaEntity entity = dynamoDBMapper.load(new PerguntaEntity(id));
        return entity ==  null ? Optional.empty() : Optional.of(entity);
    }

    @Override
    public List<PerguntaEntity> filtrar(String query) {
        Map<String, Condition> filter = new HashMap<String, Condition>();
        filter.put("idTemplate", new Condition()
                .withComparisonOperator(ComparisonOperator.CONTAINS)
                .withAttributeValueList(new AttributeValue(query)));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withScanFilter(filter);
        return dynamoDBMapper.scan(PerguntaEntity.class, scanExpression);
    }

    @Override
    public void excluir(PerguntaEntity entidade) {
        dynamoDBMapper.delete(entidade);
    }

    @Override
    public void excluir(List<PerguntaEntity> perguntas) {
        dynamoDBMapper.batchDelete(perguntas);
    }
}
