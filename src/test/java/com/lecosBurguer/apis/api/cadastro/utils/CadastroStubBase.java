package com.lecosBurguer.apis.api.cadastro.utils;

import com.lecosBurguer.apis.api.cadastro.request.Cadastro;
import com.lecosBurguer.apis.api.cadastro.request.Endereco;

public class CadastroStubBase {

    public static Cadastro.CadastroBuilder cadastroBase() {
        return Cadastro.builder()
                .nome("Leandro")
                .sobrenome("Silva")
                .telefone("11999999999")
                .email("leandro@teste.com")
                .cpfCnpj("99999999999")
                .indNotificacao(true)
                .usuario("leandro")
                .senha("123456")
                .endereco(enderecoBase().build());
    }

    public static Endereco.EnderecoBuilder enderecoBase() {
        return Endereco.builder()
                .cep("99999999")
                .logradouro("rua 1")
                .bairro("bairro 1")
                .numero("1")
                .logradouro("rua 1")
                .complemento("complemento 1")
                .uf("SP");
    }
}
