package com.lecosBurguer.apis.token.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AutenticacaoRequestDTO {
    private List<AutenticacaoWrapperItemDTO> items;
}