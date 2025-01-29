package com.lecosBurguer.apis.api.cadastro.utils;

import com.lecosBurguer.apis.api.cadastro.request.Cadastro;
import com.lecosBurguer.apis.api.cadastro.request.Endereco;
import com.lecosBurguer.apis.api.cadastro.request.Items;
import com.lecosBurguer.apis.api.cadastro.request.RequestDTO;

import java.util.ArrayList;
import java.util.List;

public class CadastroUtilsErrorEmailInvalido {

    public RequestDTO createResponseStub() {
        RequestDTO requestDTO = new RequestDTO();

        requestDTO.setItem(listItemStub());

        return requestDTO;
    }

    private List<Items> listItemStub() {

        List<Items> items = new ArrayList<>();
        Items item = new Items();

        item.setCadastro(cadastroStub());

        items.add(item);

        return items;
    }

    private Cadastro cadastroStub() {
        Cadastro cadastro = new Cadastro();

        cadastro.setNome("Leandro");
        cadastro.setSobrenome("Silva");
        cadastro.setTelefone("11999999999");
        cadastro.setEmail("emailInvalido");
        cadastro.setCpfCnpj("99999999999");
        cadastro.setIndNotificacao(true);
        cadastro.setUsuario("leandro");
        cadastro.setSenha("123456");
        cadastro.setEndereco(enderecoStub());

        return cadastro;
    }

    private Endereco enderecoStub() {
        Endereco endereco = new Endereco();

        endereco.setCep("99999999");
        endereco.setLogradouro("rua 1");
        endereco.setBairro("bairro 1");
        endereco.setNumero("1");
        endereco.setComplemento("complemento 1");
        endereco.setUf("SP");

        return endereco;
    }
}
