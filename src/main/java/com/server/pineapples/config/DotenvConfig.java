//package com.server.pineapples.config;
//
//import io.github.cdimascio.dotenv.Dotenv;
//import jakarta.annotation.PostConstruct;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DotenvConfig {
//    @PostConstruct
//    public void loadEnv() {
//        Dotenv dotenv = Dotenv.load();
//        dotenv.entries().forEach(entry ->
//                System.setProperty(entry.getKey(), entry.getValue())
//        );
//    }
//
//    @PostConstruct
//    public void logVariables() {
//        System.out.println("DATABASE_URL: " + System.getProperty("DATABASE_URL"));
//    }
//}
