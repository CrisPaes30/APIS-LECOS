package com.lecosBurguer.apis.api.cadastro.service.cadastroService.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lecosBurguer.apis.api.cadastro.service.cadastroService.CadastraUsuariokeyCloak;
import com.lecosBurguer.apis.token.brocker.AutenticacaoClient;
import com.lecosBurguer.apis.token.brocker.KeycloakService;
import com.lecosBurguer.apis.token.dto.AutenticaRequestDTO;
import com.lecosBurguer.apis.token.dto.AutenticacaoRequestDTO;
import com.lecosBurguer.apis.token.dto.AutenticacaoWrapperItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CadastraUsuarioKeyCloakImpl implements CadastraUsuariokeyCloak {

    private final KeycloakService keycloakService;
    private final AutenticacaoClient autenticacaoClient;

    @Override
    public void cadastroUsuarioKeyCloak(String username, String email, String lastName, String firstName, String roles, String password) throws JsonProcessingException {
        log.info("Realizando cadastro no Keycloak");

        String bearerToken = "Bearer " + keycloakService.getServiceToken();

        AutenticaRequestDTO request = AutenticaRequestDTO.builder()
                .usuario(username)
                .email(email)
                .nome(firstName)
                .sobrenome(lastName)
                .role(roles)
                .senha(password)
                .build();

        AutenticacaoWrapperItemDTO wrapperItem = AutenticacaoWrapperItemDTO.builder()
                .autenticaRequest(request)
                .build();

        AutenticacaoRequestDTO requestBody = AutenticacaoRequestDTO.builder()
                .items(List.of(wrapperItem))
                .build();

        autenticacaoClient.registerUser(bearerToken, requestBody);
    }
}
