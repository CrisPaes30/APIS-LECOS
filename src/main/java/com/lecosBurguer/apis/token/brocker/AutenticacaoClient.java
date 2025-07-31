package com.lecosBurguer.apis.token.brocker;

import com.lecosBurguer.apis.config.JsonFeignConfig;
import com.lecosBurguer.apis.token.dto.AutenticacaoRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "api-authentication",
        url = "http://localhost:8100/api",
        configuration = JsonFeignConfig.class
)
public interface AutenticacaoClient {

    @PostMapping(
            value = "/v1/authentication-registration",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    String registerUser(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken,
            @RequestBody AutenticacaoRequestDTO requestBody
    );
}



