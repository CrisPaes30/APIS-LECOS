package com.lecosBurguer.apis.api.valida.validaController.impl;

import com.lecosBurguer.apis.entities.LcCadastro;
import com.lecosBurguer.apis.repository.CadastroRepository;
import com.lecosBurguer.apis.utils.TokenUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = {
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"
        })
@AutoConfigureMockMvc
class ValidaControllerImplITTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenUtil tokenUtil;

    @MockBean
    private CadastroRepository cadastroRepository;

    private static final String URL = "/v1/usuarios/validar";

    @Test
    void deveRetornar401_quandoTokenInvalido() throws Exception {
        String token = "token_invalido";

        Mockito.when(tokenUtil.validarToken(token)).thenReturn(false);

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\":\"" + token + "\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.mensagem").value("Token inválido ou expirado"));
    }

    @Test
    void deveRetornar404_quandoUsuarioNaoEncontrado() throws Exception {
        String token = "token_valido";
        String email = "naoexiste@teste.com";

        Mockito.when(tokenUtil.validarToken(token)).thenReturn(true);
        Mockito.when(tokenUtil.extrairEmail(token)).thenReturn(email);
        Mockito.when(cadastroRepository.findByEmail(email)).thenReturn(null);

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\":\"" + token + "\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Usuário não encontrado"));
    }

    @Test
    void deveRetornar200_quandoValidacaoOk() throws Exception {
        String token = "token_valido";
        String email = "cliente@teste.com";

        LcCadastro usuario = new LcCadastro();
        usuario.setEmail(email);

        Mockito.when(tokenUtil.validarToken(token)).thenReturn(true);
        Mockito.when(tokenUtil.extrairEmail(token)).thenReturn(email);
        Mockito.when(cadastroRepository.findByEmail(email)).thenReturn(usuario);

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\":\"" + token + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Cadastro confirmado com sucesso!"));
    }

}