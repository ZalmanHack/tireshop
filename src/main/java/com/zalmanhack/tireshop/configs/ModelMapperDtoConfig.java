package com.zalmanhack.tireshop.configs;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
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
        Converter<LocalDateTime, String> localDateConverter = context -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(stamp);
            return context.getSource() == null ? null : context.getSource().format(formatter);
        };
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(localDateConverter);
        return modelMapper;
    }
}


