package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.requestDTO.requestCadastroDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Endereco {

    private String cep;
    private String logradouro;
    private String bairro;
    private String numero;
    private String complemento;
    private String uf;
}
