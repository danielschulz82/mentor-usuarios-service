package br.com.mentoring.exception;

import br.com.mentoring.model.Erro;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {UsuarioServiceException.class, ExceptionHandler.class})
public class ServiceExceptionHandler implements ExceptionHandler<UsuarioServiceException, HttpResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    @Override
    public HttpResponse handle(HttpRequest request, UsuarioServiceException exception) {
        LOGGER.info("Exceção do tipo {} foi tratada. Id: {} - Mensagem: {}",
                UsuarioServiceException.class,
                exception.getId(),
                exception.getMensagem());

        Erro erro = new Erro();
        erro.setId(exception.getId());
        erro.setMensagem(exception.getMensagem());
        return HttpResponse.ok(erro);
    }
}
