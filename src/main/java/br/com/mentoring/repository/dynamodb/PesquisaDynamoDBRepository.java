package br.com.mentoring.repository.dynamodb;

import br.com.mentoring.repository.PesquisaRepository;
import br.com.mentoring.repository.entity.PesquisaEntity;
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
public class PesquisaDynamoDBRepository implements PesquisaRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Inject
    public PesquisaDynamoDBRepository(AmazonDynamoDB amazonDynamoDB) {
        this.dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }

    @Override
    public void salvar(PesquisaEntity entidade) {
        dynamoDBMapper.save(entidade);
    }

    @Override
    public void atualizar(PesquisaEntity entidade) {
        this.salvar(entidade);
    }

    @Override
    public Optional<PesquisaEntity> carregar(String id) {
        PesquisaEntity entity = dynamoDBMapper.load(new PesquisaEntity(id));
        return entity ==  null ? Optional.empty() : Optional.of(entity);
    }

    @Override
    public List<PesquisaEntity> filtrar(String query) {
        if(query != null && !query.isEmpty()) {
            Map<String, Condition> filter = new HashMap<String, Condition>();
            filter.put("idTemplate", new Condition()
                    .withComparisonOperator(ComparisonOperator.EQ)
                    .withAttributeValueList(new AttributeValue(query)));

            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withScanFilter(filter);
            return dynamoDBMapper.scan(PesquisaEntity.class, scanExpression);
        }
        return dynamoDBMapper.scan(PesquisaEntity.class, new DynamoDBScanExpression());
    }

    @Override
    public void excluir(PesquisaEntity entidade) {
        dynamoDBMapper.delete(entidade);
    }
}
