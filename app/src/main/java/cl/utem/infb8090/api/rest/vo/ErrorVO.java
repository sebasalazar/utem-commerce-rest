package cl.utem.infb8090.api.rest.vo;

import java.time.LocalDateTime;

public class ErrorVO {

    public String message = null;
    public LocalDateTime date = LocalDateTime.now();

    public ErrorVO() {
        this.message = "Error genérico o desconocido";
        this.date = LocalDateTime.now();
    }

    public ErrorVO(String message) {
        this.message = message;
        this.date = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
