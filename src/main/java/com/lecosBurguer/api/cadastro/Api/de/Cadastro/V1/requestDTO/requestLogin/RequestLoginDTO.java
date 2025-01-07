package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.requestDTO.requestLogin;

import lombok.Data;

@Data
public class RequestLoginDTO {

    private String usernameOrEmail;
    private String secret;

}
