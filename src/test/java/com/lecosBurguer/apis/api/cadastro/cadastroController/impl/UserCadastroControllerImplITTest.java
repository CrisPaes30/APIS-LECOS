package com.lecosBurguer.apis.api.cadastro.cadastroController.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.lecosBurguer.apis.api.cadastro.request.Cadastro;
import com.lecosBurguer.apis.api.cadastro.request.Endereco;
import com.lecosBurguer.apis.api.cadastro.request.Items;
import com.lecosBurguer.apis.api.cadastro.request.RequestDTO;
import com.lecosBurguer.apis.api.cadastro.utils.CadastroStubBase;
import com.lecosBurguer.apis.api.response.*;
import com.lecosBurguer.apis.api.cadastro.business.CadastroBussines;

import com.lecosBurguer.apis.entities.LcCadastro;
import lombok.Builder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserCadastroControllerImplITTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CadastroBussines cadastroBussines;

    @Test
    @WithMockUser(roles = "cliente")
    void deveRetornar201QuandoCadastroComSucesso() throws Exception {
        RequestDTO requestDTO = buildRequestValido();
        ResponseDTO responseOk = buildResponseOk();

        Mockito.when(cadastroBussines.createResponse(Mockito.any(RequestDTO.class)))
                .thenReturn(responseOk);

        mockMvc.perform(post("/v1/user-registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.items[0].data.message").value("Usuário cadastrado com sucesso"))
                .andExpect(jsonPath("$.data.items[0].data.status").value("OK"));
    }


    @WithMockUser(roles = "cliente")
    @Test
    void deveRetornar207QuandoCadastroParcial() throws Exception {
        RequestDTO cadastro = RequestDTO.builder()
                .item(List.of(
                        Items.builder()
                                .cadastro(CadastroStubBase.cadastroBase().build())
                                .build()))
                .build();

        ErrorDetail errorDetail = ErrorDetail.builder()
                .code("CP_0011")
                .message("Email já cadastrado")
                .build();

        Item item = Item.builder()
                .error(List.of(errorDetail))
                .build();

        ResponseDTO responseErro = ResponseDTO.builder()
                .data(Data.builder()
                        .items(List.of(item))
                        .build())
                .build();

        Mockito.when(cadastroBussines.createResponse(Mockito.any())).thenReturn(responseErro);

        mockMvc.perform(post("/v1/user-registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cadastro)))
                .andExpect(status().isMultiStatus())
                .andExpect(jsonPath("$.data.items[0].error[0].message").value("Email já cadastrado"));
    }



    private String asJsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    @Builder
    private RequestDTO buildRequestValido() {
        Endereco endereco = Endereco.builder()
                .cep("12345-678")
                .logradouro("Rua Teste")
                .bairro("Centro")
                .numero("100")
                .complemento("Apt 1")
                .uf("SP")
                .build();

        Cadastro cadastro = Cadastro.builder()
                .nome("João")
                .sobrenome("Silva")
                .email("joao@teste.com")
                .telefone("11999999999")
                .cpfCnpj("12345678900")
                .endereco(endereco)
                .usuario("joao123")
                .senha("senha123")
                .indNotificacao(true)
                .build();

        Items item = Items.builder()
                .cadastro(cadastro).build();

        return RequestDTO.builder()
                .item(List.of(item))
                .build();
    }

    @Builder
    private ResponseDTO buildResponseOk() {
        MessageData dataInterna = MessageData.builder()
                .message("Usuário cadastrado com sucesso")
                .status("OK")
                .build();

        Item item = Item.builder()
                .data(dataInterna)
                .build();

        Data data = Data.builder()
                .items(List.of(item))
                .build();

        return ResponseDTO.builder()
                .data(data)
                .build();
    }



    @Builder
    private ResponseDTO buildResponseParcial() {
        ErrorDetail erro = ErrorDetail.builder()
                .code("EMAIL_DUPLICADO")
                .message("Email já cadastrado")
                .action("Informe outro email")
                .build();

        MessageData dataInterna = MessageData.builder()
                .message("Cadastro parcial")
                .status("ERRO")
                .build();

        Item item = Item.builder()
                .itemId("1")
                .data(dataInterna)
                .error(List.of(erro))
                .build();

        Data data = Data.builder()
                .items(List.of(item))
                .build();

        return ResponseDTO.builder()
                .data(data)
                .build();
    }


}