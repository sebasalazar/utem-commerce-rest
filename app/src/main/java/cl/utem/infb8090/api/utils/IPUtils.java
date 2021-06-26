package cl.utem.infb8090.api.utils;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPUtils implements Serializable {

    private static final String[] IP_HEADER_CANDIDATES = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "X-User-IP",
        "REMOTE_ADDR"
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(IPUtils.class);

    private IPUtils() {
        throw new IllegalStateException();
    }

    /**
     *
     * @param ip IP a considerar
     * @return true si se encontró alguna IP
     */
    private static boolean isIpFound(String ip) {
        return StringUtils.isNotBlank(ip) && !StringUtils.equalsIgnoreCase("unknown", ip);
    }

    /**
     *
     * @param text listado de ip, puede contener comas
     * @return la primera de las ip del texto
     */
    private static String splitIp(String text) {
        String ip = StringUtils.trimToEmpty(text);
        try {
            if (StringUtils.isNotBlank(ip)) {
                String[] splited = StringUtils.split(text, ",");
                if (splited != null && splited.length > 0) {
                    ip = StringUtils.trimToEmpty(splited[0]);
                    if (!InetAddressValidator.getInstance().isValid(ip)) {
                        LOGGER.error("La IP '{}' es inválida", ip);
                    }
                }
            }
        } catch (Exception e) {
            ip = StringUtils.trimToEmpty(text);
            LOGGER.error("Error al obtener una ip: {}", e.getMessage());
        }
        return ip;
    }

    /**
     *
     * @param request Petición HTTP
     * @return La IP del cliente, en función de las posiblidades
     */
    public static String getIps(HttpServletRequest request) {
        String ip = StringUtils.EMPTY;
        if (request != null) {
            for (String header : IP_HEADER_CANDIDATES) {
                ip = StringUtils.trimToEmpty(request.getHeader(header));
                if (StringUtils.isNotBlank(ip) && !StringUtils.equalsIgnoreCase(ip, "unknown")) {
                    return ip;
                }
            }
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * Obtiene la ip en función del encabezado.
     *
     * @param request Petición HTTP
     * @return Una IP del cliente
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String clientIp = null;
        int tryCount = 1;

        while (!isIpFound(clientIp) && tryCount <= 7) {
            switch (tryCount) {
                case 1:
                    clientIp = request.getHeader("X-Forwarded-For");
                    break;
                case 2:
                    clientIp = request.getHeader("Proxy-Client-IP");
                    break;
                case 3:
                    clientIp = request.getHeader("WL-Proxy-Client-IP");
                    break;
                case 4:
                    clientIp = request.getHeader("HTTP_CLIENT_IP");
                    break;
                case 5:
                    clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
                    break;
                case 6:
                    clientIp = request.getHeader("X-User-IP");
                    break;

                default:
                    clientIp = request.getRemoteAddr();
                    break;
            }

            tryCount++;
        }

        String ip = splitIp(clientIp);
        String ips = getIps(request);
        LOGGER.debug("IP: '{}' - ips '{}'", ip, ips);

        return ip;
    }
}
