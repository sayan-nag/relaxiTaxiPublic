package com.relaxiTaxi.onboarding.filter;

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


public class SessionFilter implements Filter {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String driverId = request.getHeader("driver_id");
        final String sessionID = request.getHeader("rt_d_session");
        if (driverId == null || sessionID == null) {
            response.getWriter().write("Missing driverID or SesionId in header");
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("driver_id", driverId);
        headers.add("rt_d_session", sessionID);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        restTemplate = new RestTemplate();
        String validSession = restTemplate.exchange("http://localhost:8083/session/validate", HttpMethod.GET, entity, String.class).getBody();
        if(validSession.equals("invalid session")){
            throw new ServletException("SesionId in Invalid");
        }

            //response.getWriter().write("SesionId in Invalid");
        chain.doFilter(req, res);

    }

    @Override
    public void destroy() {
        //Filter.super.destroy();
    }


}
