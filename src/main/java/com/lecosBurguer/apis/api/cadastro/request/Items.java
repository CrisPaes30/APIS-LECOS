package com.lecosBurguer.apis.api.cadastro.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Items {

    @Valid
    private Cadastro cadastro;
}
