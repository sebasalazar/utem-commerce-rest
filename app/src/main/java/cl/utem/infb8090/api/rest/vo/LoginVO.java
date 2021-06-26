package cl.utem.infb8090.api.rest.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "login")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoginVO {

    private String app = null;
    private String password = null;

    @ApiModelProperty(value = "Nombre de la aplicación que consumirá el servicio",
            required = true, example = "utem")
    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    @ApiModelProperty(value = "Contraseña usada por la aplicación que consumirá el servicio",
            required = true, example = "1234")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
