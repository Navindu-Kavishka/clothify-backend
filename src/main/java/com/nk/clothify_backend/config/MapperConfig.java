package com.nk.clothify_backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Add custom configurations here
        mapper.enable(SerializationFeature.INDENT_OUTPUT);  // Example of pretty printing JSON
        // Configure other features as required
        return mapper;
    }
}
