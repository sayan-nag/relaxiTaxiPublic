package com.relaxiTaxi.notification;

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
        "com.relaxiTaxi.notification"}, exclude = {DataSourceAutoConfiguration.class})
@Configuration

@EnableConfigurationProperties
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})

@EnableScheduling
@ComponentScan("com.relaxiTaxi.notification")
@EnableJpaRepositories(basePackages = "com.relaxiTaxi.notification.persistence")
@EntityScan("com.relaxiTaxi.notification.*")

public class NotificationApp {


    public static void main(String[] args) {
        SpringApplication springApplication =
                new SpringApplication(NotificationApp.class);
        SpringApplication.run(NotificationApp.class, args);


    }


}