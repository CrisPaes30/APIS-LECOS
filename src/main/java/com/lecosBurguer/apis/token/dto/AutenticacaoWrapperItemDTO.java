package com.lecosBurguer.apis.token.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AutenticacaoWrapperItemDTO {

    private AutenticaRequestDTO autenticaRequest;
}
