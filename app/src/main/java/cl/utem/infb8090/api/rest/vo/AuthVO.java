package cl.utem.infb8090.api.rest.vo;

import java.io.Serializable;

public class AuthVO implements Serializable {

    private static final long serialVersionUID = 8193165598058334208L;

    private String bearer = null;

    public AuthVO() {
    }

    public AuthVO(String bearer) {
        this.bearer = bearer;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }
}
