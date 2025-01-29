package com.lecosBurguer.apis.api.cadastro.service.impl;

import com.lecosBurguer.apis.api.cadastro.service.cadastroService.CadastraUsuariokeyCloak;
import com.lecosBurguer.apis.exceptions.BusinessException;
import com.lecosBurguer.apis.repository.LcKeycloakErrorCadastroRepository;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.lecosBurguer.apis.enums.CadastroEnums.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class CadastraUsuarioKeyCloakImpl implements CadastraUsuariokeyCloak {

    private final Keycloak keycloak;
    private final LcKeycloakErrorCadastroRepository lcKeycloakErrorCadastroRepository;

    @Override
    public void cadastroUsuarioKeyCloak(String username, String email, String lastName, String firstName, String roles, String password) {

        log.info("Realizando cadastro no Keycloak");

        // Criação do usuário
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setEnabled(true);

        // Adiciona a senha ao usuário
        CredentialRepresentation passwordCredential = createCredential("password", password);
        passwordCredential.setTemporary(false); // Define se a senha será temporária
        user.setCredentials(List.of(passwordCredential));

        Response response;

        // Cria o usuário no realm "lecosBurguer"
        try {
            response = keycloak.realm("lecosBurguer").users().create(user);
        } catch (BusinessException e) {
            log.error(CP_0029.getCode());
            log.info(CP_0030.getCode(), e.getMessage());

            try {
                lcKeycloakErrorCadastroRepository.insereUsuario(username, email, lastName, firstName, roles, password, "PENDENTE");
            } catch (Exception ex) {
                log.error("Erro ao inserir o cadastro no banco", ex.getMessage());
            }
            throw new BusinessException(CP_0028.getCode());
        }

        if (response.getStatus() == 201) {
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
                log.error(CP_0031.getCode(), roles, e.getMessage());
                log.info(CP_0030.getCode(), e.getMessage());
                lcKeycloakErrorCadastroRepository.insereUsuario(username, email, lastName, firstName, roles, password, "PENDENTE");
                throw new BusinessException(CP_0028.getCode());
            }
        } else {
            log.error(CP_0032.getCode(), response.getStatus());
            log.info(CP_0030.getCode(), response.getStatus());
            lcKeycloakErrorCadastroRepository.insereUsuario(username, email, lastName, firstName, roles, password, "PENDENTE");
            throw new BusinessException(CP_0028.getCode());
        }
    }

    private CredentialRepresentation createCredential(String type, String value) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(type);
        credential.setValue(value);
        credential.setTemporary(false); // Define se a credencial é temporária
        return credential;
    }
}
