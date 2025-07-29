package com.lecosBurguer.apis.api.cadastro.request.autentica;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutenticaRequestDTO  {

    private String usuario;

    private String email;

    private String nome;

    private String sobrenome;

    private String Role;

    private String senha;


}

