package com.lecosBurguer.apis.token;

import com.lecosBurguer.apis.config.KeycloakFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(
        name = "keycloakClient",
        url = "http://localhost:8080",
        configuration = KeycloakFeignConfig.class
)
public interface KeycloakClient {
    @PostMapping(
            value = "/realms/{realm}/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    Map<String, Object> getToken(
            @PathVariable("realm") String realm,
            @RequestBody Map<String, ?> form
    );
}




