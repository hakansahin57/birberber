package com.birberber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
//@ImportResource("classpath:/spring/spring-security.xml")
public class BirBerberApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BirBerberApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.servlet.context-path", "/"));
        app.run(args);
    }
}
