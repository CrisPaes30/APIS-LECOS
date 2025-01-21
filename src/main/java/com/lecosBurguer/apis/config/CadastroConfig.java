package com.lecosBurguer.apis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
@Configuration
public class CadastroConfig {

    public static final String MESSAGE_SOURCE_BEAN_IDENTIFIER = "reloadBean";

    @Bean(name = {"reloadBean"})
    public ReloadableResourceBundleMessageSource reloadBean() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:cadastro_messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(3600);
        return messageSource;
    }
}
