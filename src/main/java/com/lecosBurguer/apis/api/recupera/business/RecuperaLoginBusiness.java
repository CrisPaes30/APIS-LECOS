//package com.lecosBurguer.apis.api.recupera.business;
//
//import com.lecosBurguer.apis.api.recupera.request.RecoveryDTO;
//import com.lecosBurguer.apis.api.recupera.service.service.RecuperaLoginService;
//import com.lecosBurguer.apis.api.response.*;
//import com.lecosBurguer.apis.exceptions.BusinessException;
//import com.lecosBurguer.apis.utils.MensagemResolver;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.context.support.ReloadableResourceBundleMessageSource;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.lecosBurguer.apis.config.CadastroConfig.MESSAGE_SOURCE_BEAN_IDENTIFIER;
//
//@Service
//public class RecuperaLoginBusiness {
//
//    private final RecuperaLoginService recuperaLoginService;
//
//    private final ReloadableResourceBundleMessageSource messageSource;
//
//    private String message;
//
//    public RecuperaLoginBusiness(RecuperaLoginService recuperaLoginService,
//                            @Qualifier(MESSAGE_SOURCE_BEAN_IDENTIFIER) ReloadableResourceBundleMessageSource messageSource,
//                            MensagemResolver resolver) {
//        this.recuperaLoginService = recuperaLoginService;
//        this.messageSource = messageSource;
//    }
//
//    public ResponseDTO recupera(RecoveryDTO recoveryDTO) {
//
//        ResponseDTO responseDTO = new ResponseDTO();
//        Data data = new Data();
//        List<Item> items = new ArrayList<>();
//        Item item = new Item();
//        MessageData messageData = new MessageData();
//
//        try {
//            recuperaLoginService.recoveryUsuario(recoveryDTO.getEmail());
//            message = messageSource.getMessage("Email Encontrado", null, LocaleContextHolder.getLocale());
//
//            messageData.setMessage(message);
//            item.setData(messageData);
//
//        }catch (BusinessException e) {
//            String message = messageSource.getMessage(e.getCode(), e.getArgs(), LocaleContextHolder.getLocale());
//
//            ErrorDetail error = new ErrorDetail();
//            error.setCode(e.getCode());
//            error.setMessage(message);
//
//            error.setAction("Corrija os dados informados e tente novamente.");
//
//            List<ErrorDetail> errors = new ArrayList<>();
//            errors.add(error);
//
//            item.setError(errors);
//        }
//
//        items.add(item);
//
//        data.setItems(items);
//        responseDTO.setData(data);
//        return responseDTO;
//    }
//}
