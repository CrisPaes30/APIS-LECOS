package com.lecosBurguer.apis.api.usuario.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestLoginDTO {

    @NotBlank(message = "O campo usernameOrEmail deve ser preenchido.")
    private String usernameOrEmail;

    @NotBlank(message = "O campo secret deve ser preenchido.")
    private String secret;

}
