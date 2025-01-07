package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.response;

import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.exceptions.BusinessException;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.requestDTO.requestCadastroDTO.RequestDTO;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.response.respondeDTO.*;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.service.impl.CadastroServiceImpl;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.utils.MensagemResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.config.CadastroConfig.MESSAGE_SOURCE_BEAN_IDENTIFIER;
import static com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.enums.CadastroEnums.CP_0018;

@Service
public class ResponseBuilder {

    private final CadastroServiceImpl cadastroService;

    private final ReloadableResourceBundleMessageSource messageSource;

    private final MensagemResolver resolver;
    private String message;

    public ResponseBuilder(CadastroServiceImpl cadastroService,
                           @Qualifier(MESSAGE_SOURCE_BEAN_IDENTIFIER) ReloadableResourceBundleMessageSource messageSource,
                           MensagemResolver resolver) {
        this.cadastroService = cadastroService;
        this.messageSource = messageSource;
        this.resolver = resolver;
    }

    public ResponseDTO createResponse(RequestDTO requestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        Data data = new Data();
        List<Item> items = new ArrayList<>();

        requestDTO.getItem().forEach(requestItem -> {
            Item item = new Item();
            MessageData messageData = new MessageData();

            try {
                cadastroService.cadastro(requestDTO);

                String nomeCadastro = requestDTO.getItem().get(0).getCadastro().getNome();
                String mensagem = resolver.getMensagem(CP_0018.getCode(), nomeCadastro);

                messageData.setMessage(mensagem);
                item.setData(messageData);

            } catch (BusinessException e) {
                String testeMessage = messageSource.getMessage(e.getCode(), e.getArgs(), LocaleContextHolder.getLocale());

                ErrorDetail error = new ErrorDetail();
                error.setCode(e.getCode());
                error.setMessage(testeMessage);
                error.setAction("Corrija os dados informados e tente novamente.");

                List<ErrorDetail> errors = new ArrayList<>();
                errors.add(error);

                item.setError(errors);
            }

            items.add(item);
        });

        data.setItems(items);
        responseDTO.setData(data);
        return responseDTO;
    }
}
