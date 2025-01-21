package com.lecosBurguer.apis.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
@lombok.Data
public class ResponseDTO {

    private Data data;

}

