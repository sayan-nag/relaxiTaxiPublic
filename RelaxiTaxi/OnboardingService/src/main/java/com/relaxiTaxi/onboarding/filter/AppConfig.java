package com.relaxiTaxi.onboarding.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    @Bean
    public FilterRegistrationBean<SessionFilter> filterSessionBean() {
        FilterRegistrationBean<SessionFilter> registrationBean = new FilterRegistrationBean();
        SessionFilter customURLFilter = new SessionFilter();

        registrationBean.setFilter(customURLFilter);
        registrationBean.addUrlPatterns("/register/driver/*");
        registrationBean.addUrlPatterns("/admin/login");
        registrationBean.addUrlPatterns("/register/login");
        //registrationBean.addUrlPatterns("/driver/document/*");
        registrationBean.setOrder(1); //set precedence
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean filterTokenBean() {
        FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean();
        TokenFilter tokenFilter = new TokenFilter();

        registrationBean.setFilter(tokenFilter);
        registrationBean.addUrlPatterns("/register/driver/*");
        registrationBean.addUrlPatterns("/register/login");
        registrationBean.addUrlPatterns("/driver/document/*");
        registrationBean.addUrlPatterns("/driver/demographics/*");
        registrationBean.addUrlPatterns("/vehicle/document/*");
        registrationBean.addUrlPatterns("/admin/login");
        registrationBean.addUrlPatterns("/admin/device/*");
        registrationBean.addUrlPatterns("/admin/shipment/*");
        registrationBean.setOrder(2); //set precedence
        return registrationBean;
    }


}
