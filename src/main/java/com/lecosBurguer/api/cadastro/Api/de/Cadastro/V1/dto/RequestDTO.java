package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class RequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("items")
    private List<Items> item;

}
