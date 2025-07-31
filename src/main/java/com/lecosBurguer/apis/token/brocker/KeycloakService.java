package com.lecosBurguer.apis.token.brocker;

import com.lecosBurguer.apis.token.KeycloakClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final KeycloakClient keycloakClient;

    public String getServiceToken() {
        Map<String, Object> params = new HashMap<>();
        params.put("grant_type", "client_credentials");
        params.put("client_id", "api-authentication");
        params.put("client_secret", "4GfSsK28nBtWtkZXwQlXJXUZLfxg60Xm");

        Map<String, Object> response = keycloakClient.getToken("lecosBurguer", params);


        if (response.containsKey("access_token")) {
            return response.get("access_token").toString();
        }
        throw new RuntimeException("Erro ao obter token do Keycloak");
    }

}

