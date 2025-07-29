package com.lecosBurguer.apis.api.cadastro.utils;

import com.lecosBurguer.apis.api.cadastro.request.Cadastro;
import com.lecosBurguer.apis.api.cadastro.request.Endereco;
import com.lecosBurguer.apis.api.cadastro.request.Items;
import com.lecosBurguer.apis.api.cadastro.request.RequestDTO;

import java.util.List;

public class CadastroUtilsCepError {

    public RequestDTO createResponseStub() {
        return RequestDTO.builder()
                .item(listItemStub())
                .build();
    }

    private List<Items> listItemStub() {
        return List.of(
                Items.builder()
                        .cadastro(cadastroStub())
                        .build()
        );
    }

    private Cadastro cadastroStub() {
        return Cadastro.builder()
                .nome("Leandro")
                .sobrenome("Silva")
                .telefone("11999999999")
                .email("3hM7f@example.com")
                .cpfCnpj("99999999999")
                .indNotificacao(true)
                .usuario("leandro")
                .senha("123456")
                .endereco(enderecoStub())
                .build();
    }

    private Endereco enderecoStub() {
        return Endereco.builder()
                .logradouro("rua 1")
                .bairro("bairro 1")
                .numero("1")
                .complemento("complemento 1")
                .uf("SP")
                .build();
    }
}
