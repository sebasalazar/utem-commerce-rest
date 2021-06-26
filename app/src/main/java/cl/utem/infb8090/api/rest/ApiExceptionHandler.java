package cl.utem.infb8090.api.rest;

import cl.utem.infb8090.api.exception.CpydException;
import cl.utem.infb8090.api.rest.vo.ErrorVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = {"cl.utem.infb8090.api.rest"})
public class ApiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler({CpydException.class})
    public ResponseEntity handleException(CpydException e) {
        LOGGER.error("Se ha atrapado un error en la ejecución: {}", e.getMessage());

        final HttpStatus error = HttpStatus.valueOf(e.getHttpCode());
        final ErrorVO response = new ErrorVO(e.getMessage());

        return new ResponseEntity<>(response, error);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleException(Exception e) {
        LOGGER.error("Se ha atrapado un error en la ejecución: {}", e.getMessage());
        LOGGER.debug("Se ha atrapado un error en la ejecución: {}", e.getMessage(), e);

        final HttpStatus error = HttpStatus.INTERNAL_SERVER_ERROR;
        final ErrorVO response = new ErrorVO(e.getMessage());

        return new ResponseEntity<>(response, error);
    }
}
