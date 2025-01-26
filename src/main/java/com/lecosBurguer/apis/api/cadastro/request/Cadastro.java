package com.lecosBurguer.apis.api.cadastro.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cadastro {

    @NotBlank(message = "O campo nome deve ser preenchido.")
    private String nome;

    @NotBlank(message = "O campo sobrenome deve ser preenchido.")
    private String sobrenome;

    @NotBlank(message = "O campo email deve ser preenchido.")
    private String email;

    @NotBlank(message = "O campo telefone deve ser preenchido.")
    private String telefone;

    @NotBlank(message = "O campo cpfCnpj deve ser preenchido.")
    private String cpfCnpj;

    @Valid
    private Endereco endereco;

    @NotBlank(message = "O campo usuario deve ser preenchido.")
    private String usuario;

    @NotBlank(message = "O campo senha deve ser preenchido.")
    private String senha;

   // @NotBlank(message = "O campo indNotificacao deve ser preenchido.")
    private Boolean indNotificacao;

}
