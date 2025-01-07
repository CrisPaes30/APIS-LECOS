package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.utils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.config.CadastroConfig.MESSAGE_SOURCE_BEAN_IDENTIFIER;

@Component
public class MensagemResolver {

    private final ReloadableResourceBundleMessageSource messageSource;

    public MensagemResolver(@Qualifier(MESSAGE_SOURCE_BEAN_IDENTIFIER) ReloadableResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMensagem(String codigo, Object... args) {
        Locale locale = Locale.getDefault();
        return messageSource.getMessage(codigo, args, locale);
    }
}
