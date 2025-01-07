package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.response.respondeDTO;

import lombok.Data;

@Data
public class ErrorDetail {
    private String code;
    private String message;
    private String action;
}
