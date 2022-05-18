package com.zalmanhack.tireshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.time.Duration;
import java.time.LocalTime;

@SpringBootApplication
public class TireshopApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

//        Duration d1 = Duration.ofMinutes(45);
//        Duration d2 = Duration.ofMinutes(15);
//        System.out.println(d1.minus(d2).toMinutes());

        SpringApplication.run(TireshopApplication.class, args);
    }

}
