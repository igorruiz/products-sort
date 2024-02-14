package com.productsort.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.productsort.application.Application;

@SpringBootApplication
public class Application {
	
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).build().run(args);
    }

}
