package com.lecosBurguer.apis.api.cadastro.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("items")
    @Valid
    private List<Items> item;

}
