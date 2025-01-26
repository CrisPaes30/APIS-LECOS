package com.lecosBurguer.apis.api.cadastro.business;


import com.lecosBurguer.apis.api.cadastro.request.RequestDTO;
import com.lecosBurguer.apis.api.cadastro.service.impl.CadastroServiceImpl;
import com.lecosBurguer.apis.api.cadastro.utils.CadastroUtilsError;
import com.lecosBurguer.apis.api.cadastro.utils.CadastroUtilsSucesso;
import com.lecosBurguer.apis.api.response.ResponseDTO;
import com.lecosBurguer.apis.exceptions.BusinessException;
import com.lecosBurguer.apis.utils.MensagemResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import static com.lecosBurguer.apis.config.CadastroConfig.MESSAGE_SOURCE_BEAN_IDENTIFIER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastroBusinessTest {

    @InjectMocks
    private CadastroBussines cadastroBussines;

    @Mock
    private CadastroServiceImpl cadastroService;

    @Mock
    private ReloadableResourceBundleMessageSource messageSource;

    @Mock
    @Qualifier(MESSAGE_SOURCE_BEAN_IDENTIFIER)
    private MensagemResolver resolver;

    private String message;

    private final String CP_000 = "CP_000";
    private final String CP_0028 = "CP-0028";



    @Test
    void testeDevecriarResponseComSucesso() {

        CadastroUtilsSucesso cadastroUtilsSucesso = new CadastroUtilsSucesso();

        doNothing().when(cadastroService).cadastro(any(RequestDTO.class));

        cadastroBussines.createResponse(cadastroUtilsSucesso.createResponseStub());

        verify(cadastroService, times(1)).cadastro(any(RequestDTO.class));
    }

    @Test
    void testeDevecriarResponseComErro() {

        CadastroUtilsError cadastroUtilsError = new CadastroUtilsError();

        doThrow(new BusinessException(CP_000)).when(cadastroService).cadastro(any(RequestDTO.class));
        when(messageSource.getMessage(any(), any(), any())).thenReturn(null);

        ResponseDTO response = cadastroBussines.createResponse(cadastroUtilsError.createResponseStub());

        assertNotNull(response);
        assertFalse(response.getData().getItems().isEmpty());
        assertNotNull(response.getData().getItems().get(0).getError());
        assertEquals(CP_000, response.getData().getItems().get(0).getError().get(0).getCode());
        assertEquals(null, response.getData().getItems().get(0).getError().get(0).getMessage());

    }

    @Test
    void testeDevecriarResponseComErroComAMenssagemCP_0028() {

        CadastroUtilsError cadastroUtilsError = new CadastroUtilsError();

        doThrow(new BusinessException(CP_0028)).when(cadastroService).cadastro(any(RequestDTO.class));
        when(messageSource.getMessage(any(), any(), any())).thenReturn(null);

        ResponseDTO response = cadastroBussines.createResponse(cadastroUtilsError.createResponseStub());

        assertNotNull(response);
        assertFalse(response.getData().getItems().isEmpty());
        assertNotNull(response.getData().getItems().get(0).getError());
        assertEquals(CP_0028, response.getData().getItems().get(0).getError().get(0).getCode());
        assertEquals(null, response.getData().getItems().get(0).getError().get(0).getMessage());

    }


}