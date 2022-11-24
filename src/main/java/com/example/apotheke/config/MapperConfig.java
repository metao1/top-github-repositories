package com.example.apotheke.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.ObjectUtils;

@Configuration
public class MapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
            .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
            .featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES)
            .featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .modulesToInstall(new JavaTimeModule())
            .modules(new JavaTimeModule())
            .build();
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName());
            sb.append(method.getName());
            for (Object param : objects) {
                if (!ObjectUtils.isEmpty(param)) {
                    sb.append(param);
                }
            }
            return sb.toString().toLowerCase();
        };
    }
}
