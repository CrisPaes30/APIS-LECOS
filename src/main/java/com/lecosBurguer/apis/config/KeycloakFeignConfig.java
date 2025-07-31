package com.lecosBurguer.apis.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakFeignConfig {
    @Bean
    public Encoder keycloakFormEncoder() {
        return new SpringFormEncoder();
    }
}


