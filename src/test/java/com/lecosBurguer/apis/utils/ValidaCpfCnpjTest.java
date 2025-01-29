package com.lecosBurguer.apis.utils;

import com.lecosBurguer.apis.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidaCpfCnpjTest {

    @InjectMocks
    private ValidaCpfCnpj validaCpfCnpj;

    private static final String CP_0015 = "CP-0015";
    private static final String CP_0016 = "CP-0016";

    @Test
    void testDeveDarSucessoQuandoCpfValido() {

        assertDoesNotThrow(() -> validaCpfCnpj.isValidCpfCnpj("296.264.890-81"));
    }

    @Test
    void testeDeveRetornarCP_0015QuandoCpfNoFormatoInvalido() {

        BusinessException exception = assertThrows(BusinessException.class,
                () -> validaCpfCnpj.isValidCpfCnpj("12345678901"));

        assertEquals(CP_0015, exception.getCode());

    }

    @Test
    void testeDeveRetornarCP_0016QuandoCnpjNoTamanhoIncorreto() {

        BusinessException exception = assertThrows(BusinessException.class,
                () -> validaCpfCnpj.isValidCpfCnpj("123456789000"));

        assertEquals(CP_0016, exception.getCode());

    }

    @Test
    void testDeveDarSucessoQuandoCnpjValido() {

        assertDoesNotThrow(() -> validaCpfCnpj.isValidCpfCnpj("00.000.000/0000-00"));
    }

}