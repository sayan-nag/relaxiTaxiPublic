package com.relaxiTaxi.onboarding.filter;

import com.relaxiTaxi.onboarding.common.CustomResponseBuilder;
import com.relaxiTaxi.onboarding.common.ResponseCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class TokenFilter implements Filter {

    RestTemplate restTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;

        final HttpServletResponse response = (HttpServletResponse) res;
        final String token = request.getHeader("token");

        if (token == null) {
            response.getWriter().write("Missing or invalid token header");
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("token", token);
        restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        String validToken = restTemplate.exchange("http://localhost:8084/token/validate", HttpMethod.GET, entity, String.class).getBody();
        if(!validToken.equals("true")){
            //ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0001);
            response.getWriter().write("Missing or invalid token header");
            return;
        }

        chain.doFilter(req, res);

    }

    @Override
    public void destroy() {
        //Filter.super.destroy();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
