package com.lecosBurguer.apis.api.cadastro.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Items {

    @Valid
    private Cadastro cadastro;
}
