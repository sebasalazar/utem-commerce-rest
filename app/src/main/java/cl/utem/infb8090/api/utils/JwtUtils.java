package cl.utem.infb8090.api.utils;

import cl.utem.infb8090.api.persistence.model.Credential;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basado en el string procesaremos varios elementos que se obtienen desde el
 * jwt
 *
 * @author ExperTI (contacto@experti.cl)
 */
public class JwtUtils implements Serializable {

    private static final long serialVersionUID = 8572177540772003840L;

    private static final String SIGN = "uyuX9f4dDYcn7y5mGWemC3ccfcDpPRL5rtji9qZkk33hxkemru9JYkqrnPvwTgD44Yd";

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    /**
     *
     * @param jwt Datos encodeados
     * @return El emisor del JWT usado para validación
     */
    public static String getIssuer(final String jwt) {
        String issuer = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotBlank(SIGN) && StringUtils.isNotBlank(jwt)) {
                Algorithm algorithm = Algorithm.HMAC256(SIGN);
                Verification verification = JWT.require(algorithm);
                JWTVerifier verifier = verification.build();
                DecodedJWT decode = verifier.verify(jwt);
                if (decode != null) {
                    issuer = StringUtils.trimToEmpty(decode.getIssuer());
                }
            }
        } catch (Exception e) {
            issuer = StringUtils.EMPTY;
            LOGGER.error("Error al obtener issuer : {}", e.getMessage());
            LOGGER.debug("Error al obtener issuer : {}", e.getMessage(), e);
        }
        return issuer;
    }

    /**
     *
     * @param jwt Datos encodeados
     * @return El procesador configurado para el uso
     */
    public static String getProcessor(final String jwt) {
        String processor = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotBlank(SIGN) && StringUtils.isNotBlank(jwt)) {
                Algorithm algorithm = Algorithm.HMAC256(SIGN);
                Verification verification = JWT.require(algorithm);
                JWTVerifier verifier = verification.build();
                DecodedJWT decode = verifier.verify(jwt);
                if (decode != null) {
                    processor = StringUtils.trimToEmpty(decode.getClaim("processor").asString());
                }
            }
        } catch (Exception e) {
            processor = StringUtils.EMPTY;
            LOGGER.error("Error al obtener procesador : {}", e.getMessage());
            LOGGER.debug("Error al obtener procesador : {}", e.getMessage(), e);
        }
        return processor;
    }

    public static String createJwt(final String issuer, final String ip, final Credential credential) {
        String jwt = StringUtils.EMPTY;
        try {
            if (credential != null) {

                Date issuedAt = new Date();
                Date expiresAt = DateUtils.addMinutes(issuedAt, 60);
                Algorithm algorithm = Algorithm.HMAC256(SIGN);
                jwt = JWT.create()
                        .withJWTId(credential.getToken())
                        .withIssuer(issuer)
                        .withIssuedAt(issuedAt)
                        .withExpiresAt(expiresAt)
                        .withClaim("processor", credential.getApp())
                        .withClaim("ip", ip)
                        .sign(algorithm);

            }
        } catch (Exception e) {
            LOGGER.error("Error al crear JWT: {}", e.getMessage());
            LOGGER.debug("Error al crear JWT: {}", e.getMessage(), e);
        }
        return jwt;
    }

    /**
     *
     * @param ip IP del cliente
     * @param jwt Datos encodeados
     * @return verdadero is autentica o falso en cualquier otro caso
     */
    public static boolean isValid(final String ip, final String jwt) {
        boolean ok = false;
        try {
            if (StringUtils.isNotBlank(SIGN) && StringUtils.isNotBlank(jwt)) {
                Algorithm algorithm = Algorithm.HMAC256(SIGN);
                Verification verification = JWT.require(algorithm);
                JWTVerifier verifier = verification.build();
                DecodedJWT decode = verifier.verify(jwt);
                if (decode != null) {
                    final String jwtIp = StringUtils.trimToEmpty(decode.getClaim("ip").asString());
                    ok = StringUtils.equals(jwtIp, ip);
                    if (!ok) {
                        LOGGER.error("IPs difieren '{}' != '{}'", jwtIp, ip);
                    }
                }
            }
        } catch (Exception e) {
            ok = false;
            LOGGER.error("{}", jwt);
            LOGGER.error("Error al decodificar JWT : {}", e.getMessage());
            LOGGER.debug("Error al decodificar JWT : {}", e.getMessage(), e);
        }
        return ok;
    }
}
