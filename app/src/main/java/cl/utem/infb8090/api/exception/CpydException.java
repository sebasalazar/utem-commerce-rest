package cl.utem.infb8090.api.exception;

/**
 *
 * @author seba
 */
public class CpydException extends RuntimeException {

    final Integer httpCode;

    public CpydException() {
        super("Error desconocido");
        this.httpCode = 412;
    }

    public CpydException(Integer httpCode, String message) {
        super(message);
        this.httpCode = httpCode;
    }

    public CpydException(String message) {
        super(message);
        this.httpCode = 412;
    }

    public CpydException(String message, Throwable cause) {
        super(message, cause);
        this.httpCode = 412;
    }

    public CpydException(Throwable cause) {
        super(cause);
        this.httpCode = 412;
    }

    public Integer getHttpCode() {
        return httpCode;
    }
}
