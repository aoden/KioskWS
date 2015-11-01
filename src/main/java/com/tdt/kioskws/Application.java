package com.tdt.kioskws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan(basePackages = "com.tdt.kioskws")
@EnableJpaRepositories(basePackages = "com.tdt.kioskws.repository")
@EntityScan(basePackages = "com.tdt.kioskws.model")
@SpringBootApplication
@ImportResource(value = { "classpath:springmvc-resteasy.xml" })
public class Application {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }
}
