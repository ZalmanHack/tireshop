package com.zalmanhack.tireshop.configs;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class ModelMapperDtoConfig {

    @Bean
    public ModelMapper modelMapper() {
        Converter<LocalDateTime, String> localDateConverter = context -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy H:m:s");
            return context.getSource() == null ? null : context.getSource().format(formatter);
        };
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(localDateConverter);
        return modelMapper;
    }
}
