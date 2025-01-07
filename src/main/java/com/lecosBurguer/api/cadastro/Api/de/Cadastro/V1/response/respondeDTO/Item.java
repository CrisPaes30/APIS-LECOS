package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.response.respondeDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Item {
    private String itemId;
    private MessageData data;
    private List<ErrorDetail> error;

}
