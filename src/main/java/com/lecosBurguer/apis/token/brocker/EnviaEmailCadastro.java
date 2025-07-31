package com.lecosBurguer.apis.token.brocker;

import com.lecosBurguer.apis.config.JsonFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "api-lecos-email", url = "http://localhost:8110/api",
        configuration = JsonFeignConfig.class)
public interface EnviaEmailCadastro {

    @PostMapping("/v1/lecos-email/send")
    String enviaEmail(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken,
                        @RequestBody Object requestBody);
}
