package cl.utem.infb8090.api.rest.v1;

import cl.utem.infb8090.api.exception.CpydException;
import cl.utem.infb8090.api.persistence.manager.CredentialManager;
import cl.utem.infb8090.api.persistence.model.Credential;
import cl.utem.infb8090.api.rest.vo.AuthVO;
import cl.utem.infb8090.api.rest.vo.ErrorVO;
import cl.utem.infb8090.api.rest.vo.LoginVO;
import cl.utem.infb8090.api.utils.IPUtils;
import cl.utem.infb8090.api.utils.JwtUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/authentications", consumes = {"application/json;charset=utf-8"}, produces = {"application/json;charset=utf-8"})
public class AuthRest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient CredentialManager credentialManager;

    @Autowired
    private transient HttpServletRequest httpRequest;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRest.class);

    @ApiOperation(value = "Permite obtener un JWT válido para consumir las otras operaciones del servicio REST")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Respuesta fue exitosa", response = AuthVO.class),
        @ApiResponse(code = 400, message = "Petición es inválida", response = ErrorVO.class),
        @ApiResponse(code = 401, message = "Acceso no autorizado", response = ErrorVO.class),
        @ApiResponse(code = 403, message = "No tiene permisos", response = ErrorVO.class),
        @ApiResponse(code = 412, message = "Falló alguna precondición", response = ErrorVO.class)
    })
    @PostMapping(value = "/login", consumes = {"application/json;charset=utf-8"}, produces = {"application/json;charset=utf-8"})
    public ResponseEntity login(@RequestBody LoginVO request) {
        /**
         * Validar que exista un objeto de entrada
         */
        if (request == null) {
            LOGGER.error("No hay cuerpo de mensaje");
            throw new CpydException(400, "La petición es inválida");
        }

        /**
         * Exista la información mínima
         */
        final String app = StringUtils.trimToEmpty(request.getApp());
        if (StringUtils.isBlank(app)) {
            LOGGER.error("Falta el parámetro del nombre de la aplicación");
            throw new CpydException("El atributo app es requerido");
        }

        final String password = StringUtils.trimToEmpty(request.getPassword());
        if (StringUtils.isBlank(password)) {
            LOGGER.error("Falta el parámetro de la contraseña de la aplicación");
            throw new CpydException("El atributo password es requerido");
        }

        final Credential credential = credentialManager.getCredentialByApp(app);
        if (credential == null) {
            LOGGER.error("Credencial no existe");
            throw new CpydException(401, "Credenciales inválidas");
        }

        if (!credential.isActive()) {
            LOGGER.error("Credencial NO activa");
            throw new CpydException(403, "No tiene permiso para acceder a este recurso");
        }

        boolean equals = StringUtils.equals(credential.getPassword(), password);
        if (!equals) {
            LOGGER.error("La contraseña fue incorrecta");
            throw new CpydException(401, "Credenciales inválidas");
        }

        final String ip = IPUtils.getClientIpAddress(httpRequest);
        if (!InetAddressValidator.getInstance().isValid(ip)) {
            LOGGER.error("IP NO válida, posible ataque");
            throw new CpydException(403, "No tiene permiso para acceder a este recurso");
        }

        final String jwt = JwtUtils.createJwt("/v1/authentications/login", ip, credential);
        if (StringUtils.isBlank(jwt)) {
            LOGGER.error("NO pude generar el JWT");
            throw new CpydException("No fue posible completar su petición");
        }

        return ResponseEntity.ok(new AuthVO(jwt));
    }
}
