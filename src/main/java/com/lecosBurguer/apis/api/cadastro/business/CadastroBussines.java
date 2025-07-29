package com.lecosBurguer.apis.api.cadastro.business;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.lecosBurguer.apis.api.cadastro.request.RequestDTO;
import com.lecosBurguer.apis.api.response.*;
import com.lecosBurguer.apis.exceptions.BusinessException;
import com.lecosBurguer.apis.api.cadastro.service.cadastroService.impl.CadastroServiceImpl;
import com.lecosBurguer.apis.utils.MensagemResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.lecosBurguer.apis.config.CadastroConfig.MESSAGE_SOURCE_BEAN_IDENTIFIER;
import static com.lecosBurguer.apis.enums.CadastroEnums.CP_0018;
import static com.lecosBurguer.apis.enums.CadastroEnums.CP_0028;

@Service
public class CadastroBussines {

    private final CadastroServiceImpl cadastroService;
    private final ReloadableResourceBundleMessageSource messageSource;
    private final MensagemResolver resolver;

    public CadastroBussines(CadastroServiceImpl cadastroService,
                            @Qualifier(MESSAGE_SOURCE_BEAN_IDENTIFIER) ReloadableResourceBundleMessageSource messageSource,
                            MensagemResolver resolver) {
        this.cadastroService = cadastroService;
        this.messageSource = messageSource;
        this.resolver = resolver;
    }

    public ResponseDTO createResponse(RequestDTO requestDTO) {
        List<Item> items = new ArrayList<>();

        requestDTO.getItem().forEach(requestItem -> {
            try {
                cadastroService.cadastro(requestDTO);

                String nomeCadastro = requestItem.getCadastro().getUsuario();
                String mensagem = resolver.getMensagem(CP_0018.getCode(), nomeCadastro);

                MessageData messageData = MessageData.builder()
                        .message(mensagem)
                        .status("Sucesso")
                        .build();

                Item item = Item.builder()
                        .data(messageData)
                        .build();

                items.add(item);

            } catch (BusinessException e) {
                String mensagem = messageSource.getMessage(e.getCode(), e.getArgs(), LocaleContextHolder.getLocale());

                ErrorDetail error = ErrorDetail.builder()
                        .code(e.getCode())
                        .message(mensagem)
                        .action(CP_0028.getCode().equals(e.getCode()) ? null : "Corrija os dados informados e tente novamente.")
                        .build();

                Item item = Item.builder()
                        .error(List.of(error))
                        .build();

                items.add(item);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        return ResponseDTO.builder()
                .data(Data.builder()
                        .items(items)
                        .build())
                .build();
    }
}

