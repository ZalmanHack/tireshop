package com.zalmanhack.tireshop;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootApplication
public class TireshopApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(TireshopApplication.class, args);
    }

}
