package com.relaxiTaxi.token;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {
        "com.relaxiTaxi.token"})
@Configuration

@EnableConfigurationProperties
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
//@ConfigurationPropertiesScan("org.gettingBetterAtThings.agent.common.beans")
@EnableScheduling
@ComponentScan("com.relaxiTaxi.token")
@EnableJpaRepositories(basePackages = "com.relaxiTaxi.token.persistence")
@EntityScan("com.relaxiTaxi.token.*")

public class TokenApp {
    public static void main(String[] args) {
        SpringApplication springApplication =
                new SpringApplication(TokenApp.class);
        SpringApplication.run(TokenApp.class, args);
    }
}