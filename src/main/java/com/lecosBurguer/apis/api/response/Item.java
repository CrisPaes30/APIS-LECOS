package com.lecosBurguer.apis.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class Item {
    private String itemId;
    private MessageData data;
    private List<ErrorDetail> error;

}
