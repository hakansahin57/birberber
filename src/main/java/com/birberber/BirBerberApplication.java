package com.birberber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.Collections;

@SpringBootApplication
@ImportResource(value = "classpath:/spring/spring-security.xml")
@EnableWebSecurity
public class BirBerberApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BirBerberApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.servlet.context-path", "/"));
        app.run(args);
    }
}
