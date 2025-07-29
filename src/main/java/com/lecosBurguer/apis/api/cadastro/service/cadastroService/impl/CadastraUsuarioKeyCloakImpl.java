package com.lecosBurguer.apis.api.cadastro.service.cadastroService.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lecosBurguer.apis.api.cadastro.service.cadastroService.CadastraUsuariokeyCloak;
import com.lecosBurguer.apis.token.brocker.AutenticacaoClient;
import com.lecosBurguer.apis.token.brocker.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CadastraUsuarioKeyCloakImpl implements CadastraUsuariokeyCloak {

    private final KeycloakService keycloakService;
    private final AutenticacaoClient autenticacaoClient;

    @Override
    public void cadastroUsuarioKeyCloak(String username, String email, String lastName, String firstName, String roles, String password) throws JsonProcessingException {

        log.info("Realizando cadastro no Keycloak");

        String token = keycloakService.getServiceToken();
        String bearerToken = "Bearer " + token;

        Map<String, Object> autenticaRequest = Map.of(
                "usuario", username,
                "email", email,
                "nome", firstName,
                "sobrenome", lastName,
                "role", roles,
                "senha", password
        );

        Map<String, Object> item = Map.of(
                "autenticaRequest", autenticaRequest
        );

        Map<String, Object> requestBody = Map.of(
                "items", List.of(item)
        );

        autenticacaoClient.registerUser(bearerToken, requestBody);
    }
}
