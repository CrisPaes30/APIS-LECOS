package com.lecosBurguer.apis.utils;

import com.lecosBurguer.apis.repository.CadastroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidaUsuarioExistenteTest {

    @InjectMocks
    ValidaUsuarioExistente validaUsuarioExistente;

    @Mock
    private CadastroRepository cadastroRepository;

    @Test
    void testDeveDarSucessoQuandoNaoExiste() {

        when(cadastroRepository.existsByClient(any())).thenReturn(false);
        assertTrue(validaUsuarioExistente.validaUsuario("user"));
    }

    @Test
    void testDeveDarErroQuandoExiste() {

        when(cadastroRepository.existsByClient(any())).thenReturn(true);
        assertFalse(validaUsuarioExistente.validaUsuario("user"));

    }

    @Test
    void testeDeveDarErroAoTentarEncontrarUsuario() {

        when(cadastroRepository.existsByClient(any())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> validaUsuarioExistente.validaUsuario("user"));
    }

}