package com.lecosBurguer.apis.token.brocker;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "api-authentication", url = "http://localhost:8100/api")
public interface AutenticacaoClient {

    @PostMapping("/v1/authentication-registration")
    String registerUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken,
                        @RequestBody Object requestBody);
}
