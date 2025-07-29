package com.lecosBurguer.apis.utils;

import com.lecosBurguer.apis.exceptions.BusinessException;
import com.lecosBurguer.apis.repository.CadastroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lecosBurguer.apis.enums.CadastroEnums.CP_0019;
import static com.lecosBurguer.apis.enums.CadastroEnums.CP_0020;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidaEmailTest {

    @InjectMocks
    private ValidaEmail validaEmail;

    @Mock
    private CadastroRepository cadastroRepository;

    @Test
    void testeDevedarErroCP_0019QuandoEmailEstiverNoFormatoInvalido() {

        String email = "emailInvalido";

        BusinessException exception = assertThrows(BusinessException.class,
                () -> validaEmail.validaEmail(email));

        assertEquals(CP_0019.getCode(), exception.getCode());

    }

    @Test
    void testDeveDarErroCP_0020QuandoEmailForDuplicado() {

        String email = "emailvalido@email.com";

        when(cadastroRepository.existsByEmail(any())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> validaEmail.validaEmail(email));

        assertEquals(CP_0020.getCode(), exception.getCode());
    }

}