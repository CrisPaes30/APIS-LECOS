package com.lecosBurguer.apis.api.cadastro.service.impl;

import com.lecosBurguer.apis.api.cadastro.service.cadastroService.CadastraUsuariokeyCloak;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CadastraUsuarioKeyCloakImpl implements CadastraUsuariokeyCloak {

    private final Keycloak keycloak;

    @Override
    public void cadastroUsuarioKeyCloak(String username, String email, String lastName, String firstName, String roles) {

        log.info("Realizando cadastro no Keycloak");

        // Criação do usuário
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setEnabled(true);

        Response response;

        // Cria o usuário no realm "lecosBurguer"
        try {
            response = keycloak.realm("lecosBurguer").users().create(user);
        } catch (Exception e) {
            log.error("Erro ao criar usuário: {}", e.getMessage());
            return;
        }

        if (response.getStatus() != 201) {
            log.info("Erro ao criar usuário: {}", response.getStatus());
            return;
        }

        // Recupera o ID do usuário criado
        String userId = response.getLocation().getPath().replaceAll(".*/(.*)$", "$1");

        // Obtém o ID do cliente (lecosBurguer)
        String clientId = keycloak.realm("lecosBurguer")
                .clients()
                .findByClientId("lecosBurguer")
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cliente 'lecosBurguer' não encontrado"))
                .getId();

        // Recupera a representação da role no nível do cliente
        RoleRepresentation clientRole = keycloak.realm("lecosBurguer")
                .clients()
                .get(clientId)
                .roles()
                .get(roles) // Role passada como parâmetro
                .toRepresentation();

        // Associa a role ao usuário no nível do cliente
        try {
            keycloak.realm("lecosBurguer")
                    .users()
                    .get(userId)
                    .roles()
                    .clientLevel(clientId)
                    .add(List.of(clientRole));

            log.info("Usuário cadastrado e role '{}' associada com sucesso.", roles);
        } catch (Exception e) {
            log.error("Erro ao associar role '{}' ao usuário: {}", roles, e.getMessage());
        }
    }
}
