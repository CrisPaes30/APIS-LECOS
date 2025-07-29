package com.lecosBurguer.apis.api.cadastro.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Endereco {

    @NotBlank(message = "O campo cep deve ser preenchido.")
    private String cep;

    @NotBlank(message = "O campo logradouro deve ser preenchido.")
    private String logradouro;

    @NotBlank(message = "O campo bairro deve ser preenchido.")
    private String bairro;

    @NotBlank(message = "O campo numero deve ser preenchido.")
    private String numero;

    private String complemento;

    @NotBlank(message = "O campo uf deve ser preenchido.")
    private String uf;
}
