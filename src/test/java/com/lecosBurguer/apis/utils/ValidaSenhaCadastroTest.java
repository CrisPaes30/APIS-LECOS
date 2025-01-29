package com.lecosBurguer.apis.utils;

import com.lecosBurguer.apis.entities.LcCadastro;
import com.lecosBurguer.apis.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidaSenhaCadastroTest {

    @InjectMocks
    ValidaSenhaCadastro validacao;

    private static final String CP_0017 = "CP-0017";

    @Test
    void testeDeveRetornarTrueQuandoASenhaForValida() {

        String senha = "123456Ab!";

        boolean valido = validacao.validaSecretForte(senha);
        assertTrue(valido);
    }

    @Test
    void testeDeveRetornarErroQuandoASenhaForInvalidaSemCaracterEspecial() {

        String senha = "123456Ab";

       BusinessException exception =  assertThrows(BusinessException.class,
                () -> validacao.validaSecretForte(senha));

       assertEquals(CP_0017, exception.getCode());
    }

    @Test
    void testeDeveRetornarErroQuandoASenhaForInvalidaSemNumero() {

        String senha = "Abcdefgh";

       BusinessException exception =  assertThrows(BusinessException.class,
                () -> validacao.validaSecretForte(senha));

       assertEquals(CP_0017, exception.getCode());
    }

    @Test
    void testeDeveRetornarErroQuandoASenhaForInvalidaSemLetra() {

        String senha = "12345678";

       BusinessException exception =  assertThrows(BusinessException.class,
                () -> validacao.validaSecretForte(senha));

       assertEquals(CP_0017, exception.getCode());
    }

    @Test
    void testeDeveRetornarErroQuandoASenhaForInvalidaSemLetraMaiuscula() {

        String senha = "123456b!";

       BusinessException exception =  assertThrows(BusinessException.class,
                () -> validacao.validaSecretForte(senha));

       assertEquals(CP_0017, exception.getCode());
    }

    @Test
    void testeDeveRetornarErroQuandoASenhaForInvalidaSemLetraMinuscula() {

        String senha = "123456B!";

       BusinessException exception =  assertThrows(BusinessException.class,
                () -> validacao.validaSecretForte(senha));

       assertEquals(CP_0017, exception.getCode());
    }

    @Test
    void testeDeveRetornarErroQuandoASenhaForInvalidaMenorQue6() {

        String senha = "12345";

       BusinessException exception =  assertThrows(BusinessException.class,
                () -> validacao.validaSecretForte(senha));

       assertEquals(CP_0017, exception.getCode());
    }

}