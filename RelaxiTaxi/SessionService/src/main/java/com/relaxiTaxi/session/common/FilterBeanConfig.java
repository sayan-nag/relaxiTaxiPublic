package com.relaxiTaxi.session.common;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterBeanConfig {
   /* @Bean
    public FilterRegistrationBean requestMemeFilter() {
        SessionFilter sessionFilter = new SessionFilter();
        final FilterRegistrationBean reg = new FilterRegistrationBean(sessionFilter);
        reg.addUrlPatterns("/*");
        reg.setOrder(0); //defines filter execution order
        return reg;
    }*/
}