package com.lecosBurguer.apis.token;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "keycloak-client", url = "http://localhost:8080")
public interface KeycloakClient {

    @PostMapping(value = "/realms/{realm}/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Map<String, Object> getToken(@PathVariable("realm") String realm,
                                 @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType,
                                 @RequestBody MultiValueMap<String, String> body);
}

