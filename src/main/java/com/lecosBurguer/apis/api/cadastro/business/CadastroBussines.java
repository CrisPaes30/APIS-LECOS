package com.lecosBurguer.apis.api.cadastro.business;


import com.lecosBurguer.apis.api.cadastro.request.RequestDTO;
import com.lecosBurguer.apis.api.response.*;
import com.lecosBurguer.apis.exceptions.BusinessException;
import com.lecosBurguer.apis.api.cadastro.service.impl.CadastroServiceImpl;
import com.lecosBurguer.apis.utils.MensagemResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.lecosBurguer.apis.config.CadastroConfig.MESSAGE_SOURCE_BEAN_IDENTIFIER;
import static com.lecosBurguer.apis.enums.CadastroEnums.CP_0018;

@Service
public class CadastroBussines {

    private final CadastroServiceImpl cadastroService;

    private final ReloadableResourceBundleMessageSource messageSource;

    private final MensagemResolver resolver;

    private String message;

    public CadastroBussines(CadastroServiceImpl cadastroService,
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
                message = resolver.getMensagem(CP_0018.getCode(), nomeCadastro);

                messageData.setMessage(message);
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
        });

        data.setItems(items);
        responseDTO.setData(data);
        return responseDTO;
    }
}
