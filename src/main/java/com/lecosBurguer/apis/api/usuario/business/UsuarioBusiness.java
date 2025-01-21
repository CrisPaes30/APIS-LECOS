package com.lecosBurguer.apis.api.usuario.business;


import com.lecosBurguer.apis.utils.MensagemResolver;
import com.lecosBurguer.apis.api.response.*;
import com.lecosBurguer.apis.api.usuario.request.RequestLoginDTO;
import com.lecosBurguer.apis.api.usuario.service.impl.UserServiceImpl;
import com.lecosBurguer.apis.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.lecosBurguer.apis.config.CadastroConfig.MESSAGE_SOURCE_BEAN_IDENTIFIER;
import static com.lecosBurguer.apis.enums.CadastroEnums.CP_0026;

@Service
public class UsuarioBusiness {

    private UserServiceImpl userService;

    private final ReloadableResourceBundleMessageSource messageSource;

    private final MensagemResolver resolver;

    private String message;

    public UsuarioBusiness(UserServiceImpl userService,
                           @Qualifier(MESSAGE_SOURCE_BEAN_IDENTIFIER) ReloadableResourceBundleMessageSource messageSource, MensagemResolver resolver) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.resolver = resolver;
    }

    public ResponseDTO createResponseBuilder(RequestLoginDTO requestLoginDTO) {
        ResponseDTO response = new ResponseDTO();
        MessageData messageData = new MessageData();
        Data data = new Data();
        List<Item> items = new ArrayList<>();
        Item item = new Item();

        try {
            userService.authenticate(requestLoginDTO.getUsernameOrEmail(), requestLoginDTO.getSecret());

            message = resolver.getMensagem(CP_0026.getCode());

            messageData.setMessage(message);
            messageData.setStatus("Sucesso");

            item.setData(messageData);

        } catch (BusinessException e) {
            message = messageSource.getMessage(e.getCode(), e.getArgs(), LocaleContextHolder.getLocale());

            ErrorDetail error = new ErrorDetail();
            error.setCode(e.getCode());
            error.setMessage(message);
            error.setAction("Corrija os dados informados e tente novamente.");

            List<ErrorDetail> errors = new ArrayList<>();
            errors.add(error);

            item.setError(errors);

        }
        items.add(item);
        data.setItems(items);
        response.setData(data);
        return response;
    }
}
