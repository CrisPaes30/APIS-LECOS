package com.lecosBurguer.apis.config;

import feign.codec.Encoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;

@Configuration
public class JsonFeignConfig {

    private final ObjectFactory<HttpMessageConverters> messageConverters;

    public JsonFeignConfig(ObjectFactory<HttpMessageConverters> messageConverters) {
        this.messageConverters = messageConverters;
    }

    @Bean
    public Encoder jsonEncoder() {
        return new SpringEncoder(messageConverters);
    }
}
