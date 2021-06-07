package br.com.mentoring.repository.dynamodb;

import br.com.mentoring.repository.TemplatePesquisaRepository;
import br.com.mentoring.repository.entity.TemplatePesquisaEntity;
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
public class TemplatePesquisaDynamoDBRepository implements TemplatePesquisaRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Inject
    public TemplatePesquisaDynamoDBRepository(AmazonDynamoDB amazonDynamoDB) {
        this.dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }

    @Override
    public void salvar(TemplatePesquisaEntity entidade) {
        dynamoDBMapper.save(entidade);
    }

    @Override
    public void atualizar(TemplatePesquisaEntity entidade) {
        this.salvar(entidade);
    }

    @Override
    public Optional<TemplatePesquisaEntity> carregar(String id) {
        TemplatePesquisaEntity entity = dynamoDBMapper.load(new TemplatePesquisaEntity(id));
        return entity ==  null ? Optional.empty() : Optional.of(entity);
    }

    @Override
    public List<TemplatePesquisaEntity> filtrar(String query) {
        if(query != null && !query.isEmpty()) {
            Map<String, Condition> filter = new HashMap<String, Condition>();
            filter.put("descricao", new Condition()
                    .withComparisonOperator(ComparisonOperator.CONTAINS)
                    .withAttributeValueList(new AttributeValue(query)));

            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withScanFilter(filter);
            return dynamoDBMapper.scan(TemplatePesquisaEntity.class, scanExpression);
        }
        return dynamoDBMapper.scan(TemplatePesquisaEntity.class, new DynamoDBScanExpression());
    }

    @Override
    public void excluir(TemplatePesquisaEntity entidade) {
        dynamoDBMapper.delete(entidade);
    }
}
