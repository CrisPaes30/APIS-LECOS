package com.lecosBurguer.apis.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetail {
    private String code;
    private String message;
    private String action;
}
