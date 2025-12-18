package br.bruckner.jakarta.hello;
/*

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
*/
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Set;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        // Coleta todas as violações
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        // Monta uma mensagem de erro
        StringBuilder errorMessage = new StringBuilder("Erro(s) de validação: ");
        for (ConstraintViolation<?> violation : violations) {
            errorMessage.append(violation.getPropertyPath())
                    .append(" ")
                    .append(violation.getMessage())
                    .append("; ");
        }

        // Retorna a resposta com o status 400 (Bad Request)
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorMessage.toString())
                .build();
    }
}

