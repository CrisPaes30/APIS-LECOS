package com.lecosBurguer.apis.token.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AutenticaRequestDTO {
    private String usuario;
    private String email;
    private String nome;
    private String sobrenome;
    private String role;
    private String senha;
}
