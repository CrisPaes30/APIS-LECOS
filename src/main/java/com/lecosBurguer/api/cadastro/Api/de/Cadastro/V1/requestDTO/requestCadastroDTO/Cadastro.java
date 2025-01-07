package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.requestDTO.requestCadastroDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cadastro {

    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String cpfCnpj;
    private Endereco endereco;
    private String user;
    private String secret;
    private String indNotificacao;

}
