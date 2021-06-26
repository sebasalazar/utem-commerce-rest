package cl.utem.infb8090.api.rest.filter;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

public class SimpleCorsFilter implements Filter {

    private static final Locale CL = new Locale("es", "CL");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setLocale(CL);
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Credentials, Access-Control-Allow-Origin, Origin, Accept, Content-Type, Authentication");

        final String method = StringUtils.trimToEmpty(request.getMethod());
        if (StringUtils.equalsIgnoreCase("OPTIONS", method)) {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204
            return;
        } else {
            final String origin = StringUtils.trimToEmpty(request.getHeader("Origin"));
            if (StringUtils.isBlank(origin) || StringUtils.equalsAnyIgnoreCase(origin, "localhost", "localhost.localdomain", "127.0.0.1", "::1", "null")) {
                response.addHeader("Access-Control-Allow-Origin", "*");
            } else {
                response.addHeader("Access-Control-Allow-Origin", origin);
            }
            response.addHeader("Access-Control-Allow-Credentials", "true");
        }
        chain.doFilter(request, response);
    }

}
