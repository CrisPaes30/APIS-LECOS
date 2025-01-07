package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.response.respondeDTO;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.Data
public class ResponseDTO {
    private Data data;

}

