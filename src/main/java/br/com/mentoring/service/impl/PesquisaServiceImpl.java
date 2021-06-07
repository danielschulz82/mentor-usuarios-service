package br.com.mentoring.service.impl;

import br.com.mentoring.exception.TipoErro;
import br.com.mentoring.exception.UsuarioServiceException;
import br.com.mentoring.model.Pergunta;
import br.com.mentoring.model.Pesquisa;
import br.com.mentoring.model.TemplatePesquisa;
import br.com.mentoring.repository.ModelToEntityMapper;
import br.com.mentoring.repository.PerguntaRepository;
import br.com.mentoring.repository.PesquisaRepository;
import br.com.mentoring.repository.TemplatePesquisaRepository;
import br.com.mentoring.repository.entity.PerguntaEntity;
import br.com.mentoring.repository.entity.PesquisaEntity;
import br.com.mentoring.repository.entity.TemplatePesquisaEntity;
import br.com.mentoring.service.PesquisaService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class PesquisaServiceImpl implements PesquisaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PesquisaServiceImpl.class);

    @Inject
    private PerguntaRepository perguntaRepository;

    @Inject
    private TemplatePesquisaRepository templatePesquisaRepository;

    @Inject
    private PesquisaRepository pesquisaRepository;

    @Override
    public void salvarTemplatePesquisa(TemplatePesquisa templatePesquisa) {
        LOGGER.debug("Salvando novo template de pesquisa...");
        templatePesquisa.setIdTemplate(UUID.randomUUID().toString());
        TemplatePesquisaEntity entity = ModelToEntityMapper.toEntity(templatePesquisa);
        templatePesquisaRepository.salvar(entity);
    }

    @Override
    @SneakyThrows
    public void atualizarTemplatePesquisa(TemplatePesquisa templatePesquisa) {
        LOGGER.debug("Atualizando dados do template de pesquisa...");
        Optional<TemplatePesquisaEntity> optTemplate = templatePesquisaRepository.carregar(templatePesquisa.getIdTemplate());
        if(optTemplate.isEmpty()) {
            LOGGER.warn("Template de pesquisa não encontrado para atualização. Id: {}", templatePesquisa.getIdTemplate());
            throw new UsuarioServiceException(TipoErro.MUS03_TEMPLATE_PESQUISA_NAO_ENCONTRADO);
        }

        TemplatePesquisaEntity entidade = optTemplate.get();
        entidade.setDescricao(templatePesquisa.getDescricao());
        templatePesquisaRepository.atualizar(entidade);
    }

    @Override
    @SneakyThrows
    public TemplatePesquisa carregarTemplatePesquisa(String id) {
        LOGGER.debug("Carregando dados do template de pesquisa...");
        Optional<TemplatePesquisaEntity> optTemplate = templatePesquisaRepository.carregar(id);
        if(optTemplate.isEmpty()) {
            LOGGER.warn("Template de pesquisa não encontrado. Id: {}", id);
            throw new UsuarioServiceException(TipoErro.MUS03_TEMPLATE_PESQUISA_NAO_ENCONTRADO);
        }

        return ModelToEntityMapper.toModel(optTemplate.get());
    }

    @Override
    public List<TemplatePesquisa> listaTemplatePesquisa(String filtro) {
        LOGGER.debug("Listando templates de pesquisa com filtro: {}", filtro);
        List<TemplatePesquisa> templates = new ArrayList<>();
        templatePesquisaRepository.filtrar(filtro).forEach(item -> {
            templates.add(ModelToEntityMapper.toModel(item));
        });
        return templates;
    }

    @Override
    @SneakyThrows
    public void excluirTemplatePesquisa(String id) {
        LOGGER.debug("Excluindo dados do template de pesquisa...");

        if(!pesquisaRepository.filtrar(id).isEmpty()) {
            LOGGER.warn("Pergunta não encontrada para exclusão. Id: {}", id);
            throw new UsuarioServiceException(TipoErro.MUS05_PESQUISA_POSSUI_RESPOSTAS);
        }

        Optional<TemplatePesquisaEntity> optTemplate = templatePesquisaRepository.carregar(id);
        if(optTemplate.isEmpty()) {
            LOGGER.warn("Pergunta não encontrada para exclusão. Id: {}", id);
            throw new UsuarioServiceException(TipoErro.MUS02_PERGUNTA_NAO_ENCONTRADA);
        }

        List<PerguntaEntity> perguntasPesquisa = perguntaRepository.filtrar(id);
        if(!perguntasPesquisa.isEmpty()) {
            perguntaRepository.excluir(perguntasPesquisa);
        }

        templatePesquisaRepository.excluir(optTemplate.get());
    }

    @Override
    public void salvarPergunta(Pergunta pergunta) {
        LOGGER.debug("Salvando nova pergunta...");
        pergunta.setId(UUID.randomUUID().toString());
        pergunta.getOpcoesResposta().forEach(opcao -> {
            opcao.setIdOpcao(UUID.randomUUID().toString());
        });

        PerguntaEntity entity = ModelToEntityMapper.toEntity(pergunta);
        perguntaRepository.salvar(entity);
    }

    @Override
    @SneakyThrows
    public void atualizarPergunta(Pergunta pergunta) {
        LOGGER.debug("Atualizando dados da pergunta...");
        Optional<PerguntaEntity> optPergunta = perguntaRepository.carregar(pergunta.getId());
        if(optPergunta.isEmpty()) {
            LOGGER.warn("Pergunta não encontrado para atualização. Id: {}", pergunta.getId());
            throw new UsuarioServiceException(TipoErro.MUS02_PERGUNTA_NAO_ENCONTRADA);
        }

        PerguntaEntity entidade = optPergunta.get();
        if(entidade.getOpcoesResposta().size() != pergunta.getOpcoesResposta().size()) {
            pergunta.getOpcoesResposta().forEach(opcao -> {
                opcao.setIdOpcao(UUID.randomUUID().toString());
            });
        }

        entidade = ModelToEntityMapper.toEntity(pergunta);
        perguntaRepository.atualizar(entidade);
    }

    @Override
    @SneakyThrows
    public Pergunta carregarPergunta(String id) {
        LOGGER.debug("Carregando dados da pergunta...");
        Optional<PerguntaEntity> optPergunta = perguntaRepository.carregar(id);
        if(optPergunta.isEmpty()) {
            LOGGER.warn("Pergunta não encontrada. Id: {}", id);
            throw new UsuarioServiceException(TipoErro.MUS02_PERGUNTA_NAO_ENCONTRADA);
        }

        return ModelToEntityMapper.toModel(optPergunta.get());
    }

    @Override
    public List<Pergunta> listaPerguntas(String filtro) {
        LOGGER.debug("Listando perguntas com filtro: {}", filtro);
        List<Pergunta> perguntas = new ArrayList<>();
        perguntaRepository.filtrar(filtro).forEach(item -> {
            perguntas.add(ModelToEntityMapper.toModel(item));
        });
        return perguntas;
    }

    @Override
    @SneakyThrows
    public void excluirPergunta(String id) {
        LOGGER.debug("Excluindo dados da pergunta...");
        Optional<PerguntaEntity> optPergunta = perguntaRepository.carregar(id);
        if(optPergunta.isEmpty()) {
            LOGGER.warn("Pergunta não encontrada para exclusão. Id: {}", id);
            throw new UsuarioServiceException(TipoErro.MUS02_PERGUNTA_NAO_ENCONTRADA);
        }

        perguntaRepository.excluir(optPergunta.get());
    }

    @Override
    public void salvarPesquisa(Pesquisa pesquisa) {
        LOGGER.debug("Salvando dados da pesquisa respondida pelo usuario {}}", pesquisa.getIdUsuario());
        pesquisaRepository.salvar(ModelToEntityMapper.toEntity(pesquisa));
    }

    @Override
    @SneakyThrows
    public Pesquisa carregarPesquisa(String idPesquisa) {
        LOGGER.debug("Carregando dados da pesquisa...");
        Optional<PesquisaEntity> optPesquisa = pesquisaRepository.carregar(idPesquisa);
        if(optPesquisa.isEmpty()) {
            LOGGER.warn("Pequisa não encontrada. Id: {}", idPesquisa);
            throw new UsuarioServiceException(TipoErro.MUS04_PESQUISA_NAO_ENCONTRADA);
        }

        return ModelToEntityMapper.toModel(optPesquisa.get());
    }

    @Override
    public List<Pesquisa> listaResultados(String idTemplate) {
        LOGGER.debug("Carregando lista de pesquisas respondidas...");
        List<Pesquisa> pesquisas = new ArrayList<>();
        pesquisaRepository.filtrar(idTemplate).forEach(resultado -> {
            pesquisas.add(ModelToEntityMapper.toModel(resultado));
        });
        return pesquisas;
    }
}
