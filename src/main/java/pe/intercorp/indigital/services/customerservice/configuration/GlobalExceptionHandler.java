package pe.intercorp.indigital.services.customerservice.configuration;

import org.springframework.beans.TypeMismatchException;
import org.springframework.core.codec.DecodingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.intercorp.indigital.services.customerservice.exceptions.ErrorMessage;

import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolationException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            DecodingException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            BindException.class,
            ConstraintViolationException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage invalidRequest(Exception ex) {
        return ErrorMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("La petición es inválida. Por favor, validar request.\n " + ex.getLocalizedMessage())
                .build();
    }


    @ExceptionHandler({
            EntityExistsException.class,
            DataIntegrityViolationException.class
    })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage invalidEntityContent(Exception ex) {
        return ErrorMessage.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message(ex.getLocalizedMessage())
                .build();
    }
}
