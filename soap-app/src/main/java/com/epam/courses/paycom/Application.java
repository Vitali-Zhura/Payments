package com.epam.courses.paycom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages ={"com.epam.courses.paycom.dao;", "com.epam.courses.paycom.model;", "com.epam.courses.paycom.service;"})
public class Application  {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}


