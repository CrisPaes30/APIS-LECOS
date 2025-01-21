package com.lecosBurguer.apis.api.response;

import lombok.Data;

@Data
public class ErrorDetail {
    private String code;
    private String message;
    private String action;
}
