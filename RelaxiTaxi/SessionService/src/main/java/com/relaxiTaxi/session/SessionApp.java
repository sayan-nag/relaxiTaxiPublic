package com.relaxiTaxi.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {
        "com.relaxiTaxi.session"}, exclude = {DataSourceAutoConfiguration.class})
@Configuration

@EnableConfigurationProperties
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})

@EnableScheduling
@ComponentScan("com.relaxiTaxi.session")
@EnableJpaRepositories(basePackages = "com.relaxiTaxi.session.persistence")
@EntityScan("com.relaxiTaxi.session.*")
public class SessionApp {
    public static void main(String[] args) {

        SpringApplication springApplication =
                new SpringApplication(SessionApp.class);
        SpringApplication.run(SessionApp.class, args);
    }
}