package cl.utem.infb8090.api;

import cl.utem.infb8090.api.rest.filter.SimpleCorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiApplication {

    @Bean
    public FilterRegistrationBean<SimpleCorsFilter> simpleCorsFilter() {
        FilterRegistrationBean<SimpleCorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SimpleCorsFilter());
        registrationBean.addUrlPatterns("/v1/");
        return registrationBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
