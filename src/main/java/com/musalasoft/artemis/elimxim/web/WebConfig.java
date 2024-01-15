package com.musalasoft.artemis.elimxim.web;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.springmvc.OpenApiValidationFilter;
import com.atlassian.oai.validator.springmvc.OpenApiValidationInterceptor;
import com.musalasoft.artemis.elimxim.web.converter.DroneFilterConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public OpenApiValidationFilter openApiValidationFilter() {
        return new OpenApiValidationFilter(true, true);
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OpenApiValidationInterceptor(
                OpenApiInteractionValidator.createFor("/oas/drone-api.yml").build())
        );
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DroneFilterConverter());
    }
}
