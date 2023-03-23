package com.relaxiTaxi.onboarding;

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
        "com.relaxiTaxi.onboarding"}, exclude = {com.relaxiTaxi.onboarding.persistence.DataSourceConfiguration.class})
@Configuration

@EnableConfigurationProperties
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
//@ConfigurationPropertiesScan("org.gettingBetterAtThings.agent.common.beans")
@EnableScheduling
@ComponentScan("com.relaxiTaxi.onboarding")
@EnableJpaRepositories(basePackages = "com.relaxiTaxi.onboarding.persistence")
@EntityScan("com.relaxiTaxi.onboarding.*")

public class OnBoardingApp {


    public static void main(String[] args) {
        System.out.println("Hello world!");

        SpringApplication springApplication =
                new SpringApplication(OnBoardingApp.class);


        SpringApplication.run(OnBoardingApp.class, args);


    }


}