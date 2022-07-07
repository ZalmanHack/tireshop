package com.zalmanhack.tireshop.configs;

import org.modelmapper.AbstractProvider;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.module.jdk8.Jdk8Module;
import org.modelmapper.module.jsr310.Jsr310Module;
import org.modelmapper.module.jsr310.Jsr310ModuleConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperDtoConfig {

    @Value("${entity.datetime.stamp}")
    private String stamp;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        Jsr310ModuleConfig config = Jsr310ModuleConfig.builder()
                .dateTimePattern(stamp) // default is yyyy-MM-dd HH:mm:ss
                .build();
        modelMapper.registerModule(new Jsr310Module(config));
        modelMapper.registerModule(new Jdk8Module());
        return modelMapper;
    }
}


