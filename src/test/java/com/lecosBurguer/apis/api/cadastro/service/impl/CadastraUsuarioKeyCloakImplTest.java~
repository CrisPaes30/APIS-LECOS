package com.lecosBurguer.apis.api.cadastro.service.impl;

import com.lecosBurguer.apis.exceptions.BusinessException;
import com.lecosBurguer.apis.repository.LcKeycloakErrorCadastroRepository;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastraUsuarioKeyCloakImplTest {

    @InjectMocks
    private CadastraUsuarioKeyCloakImpl cadastraUsuarioKeyCloak;

    @Mock
    private Keycloak keycloak;

    @Mock
    private LcKeycloakErrorCadastroRepository lcKeycloakErrorCadastroRepository;

    @Mock
    private Logger logger;

    private static final String CP_0028 = "CP-0028";

    @Test
    void testeDeveCadastroUsuarioNoKeyCloakComSucesso() {
        Response response = mock(Response.class);
        URI uri = mock(URI.class);

        when(response.getStatus()).thenReturn(201);
        when(response.getLocation()).thenReturn(uri);
        when(uri.getPath()).thenReturn("/realms/myrealm/users/12345");

        RealmResource realmResource = mock(RealmResource.class);
        when(keycloak.realm("lecosBurguer")).thenReturn(realmResource);

        var clientsResource = mock(org.keycloak.admin.client.resource.ClientsResource.class);
        var clientResource = mock(org.keycloak.admin.client.resource.ClientResource.class);
        var rolesResource = mock(org.keycloak.admin.client.resource.RolesResource.class);
        var roleResource = mock(org.keycloak.admin.client.resource.RoleResource.class);

        when(realmResource.clients()).thenReturn(clientsResource);
        when(clientsResource.get("mock-client-id")).thenReturn(clientResource);
        when(clientResource.roles()).thenReturn(rolesResource);
        when(rolesResource.get("mock-role")).thenReturn(roleResource);
        when(realmResource.clients()).thenReturn(clientsResource);

        var clientRepresentation = mock(org.keycloak.representations.idm.ClientRepresentation.class);
        when(clientRepresentation.getId()).thenReturn("mock-client-id");

        when(clientsResource.findByClientId("lecosBurguer")).thenReturn(List.of(clientRepresentation));

        RoleRepresentation roleRepresentation = mock(RoleRepresentation.class);
        when(roleResource.toRepresentation()).thenReturn(roleRepresentation);

        var usersResource = mock(org.keycloak.admin.client.resource.UsersResource.class);
        var userResource = mock(org.keycloak.admin.client.resource.UserResource.class);
        var roleMappingResource = mock(org.keycloak.admin.client.resource.RoleMappingResource.class);

        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.get("12345")).thenReturn(userResource);
        when(userResource.roles()).thenReturn(roleMappingResource);

        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.create(any())).thenReturn(response);

        var roleScopeResource = mock(org.keycloak.admin.client.resource.RoleScopeResource.class);
        when(userResource.roles().clientLevel("mock-client-id")).thenReturn(roleScopeResource);

        doNothing().when(roleScopeResource).add(List.of(roleRepresentation));

        cadastraUsuarioKeyCloak.cadastroUsuarioKeyCloak("username", "email", "lastName", "firstName", "mock-role", "password");

        assertDoesNotThrow(() -> cadastraUsuarioKeyCloak.cadastroUsuarioKeyCloak("username", "email", "lastName", "firstName", "mock-role", "password"));
        verify(clientsResource, times(2)).get("mock-client-id"); // Verifica se o client foi acessado
        verify(rolesResource, times(2)).get("mock-role"); // Verifica se a role foi acessada
        verify(roleScopeResource, times(2)).add(List.of(roleRepresentation)); // Verifica se a role foi adicionada
    }

    @Test
    void testeDeveLancarErroCP_0028QuandoNaoConseguirCriarUsuarioNoKeyCloak() {

        Response response = mock(Response.class);

        RealmResource realmResource = mock(RealmResource.class);
        var usersResource = mock(org.keycloak.admin.client.resource.UsersResource.class);

        when(keycloak.realm(any())).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.create(any())).thenReturn(response);

        doNothing().when(lcKeycloakErrorCadastroRepository).insereUsuario(any(), any(), any(), any(), any(), any(), any());

        when(keycloak.realm("lecosBurguer").users().create(any()))
                .thenThrow(new BusinessException(CP_0028));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastraUsuarioKeyCloak.cadastroUsuarioKeyCloak("username", "email", "lastName", "firstName", "mock-role", "password")
        );

        assertEquals(CP_0028 + " - []", exception.getMessage());
        verify(lcKeycloakErrorCadastroRepository, times(1)).insereUsuario(any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void testDeveDarErroCP_0028QuandoNaoConseguirCriarUsuarioNoKeyCloakStatusDiferenteDe201() {
        Response response = mock(Response.class);

        when(response.getStatus()).thenReturn(500);

        RealmResource realmResource = mock(RealmResource.class);
        var usersResource = mock(org.keycloak.admin.client.resource.UsersResource.class);

        when(keycloak.realm(any())).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.create(any())).thenReturn(response);

        doNothing().when(lcKeycloakErrorCadastroRepository).insereUsuario(any(), any(), any(), any(), any(), any(), any());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastraUsuarioKeyCloak.cadastroUsuarioKeyCloak("username", "email", "lastName", "firstName", "mock-role", "password")
        );

        assertEquals(CP_0028 + " - []", exception.getMessage());
        verify(lcKeycloakErrorCadastroRepository, times(1)).insereUsuario(any(), any(), any(), any(), any(), any(), any());
    }

    @Test
    void testeDeveDarErroCP_0028QuandoNaoConseguirAssociarRoleAoNivelDoClient() {

        Response response = mock(Response.class);
        URI uri = mock(URI.class);

        when(response.getStatus()).thenReturn(201);
        when(response.getLocation()).thenReturn(uri);
        when(uri.getPath()).thenReturn("/realms/myrealm/users/12345");

        RealmResource realmResource = mock(RealmResource.class);
        when(keycloak.realm("lecosBurguer")).thenReturn(realmResource);

        var clientsResource = mock(org.keycloak.admin.client.resource.ClientsResource.class);
        var clientResource = mock(org.keycloak.admin.client.resource.ClientResource.class);
        var rolesResource = mock(org.keycloak.admin.client.resource.RolesResource.class);
        var roleResource = mock(org.keycloak.admin.client.resource.RoleResource.class);

        when(realmResource.clients()).thenReturn(clientsResource);
        when(clientsResource.get("mock-client-id")).thenReturn(clientResource);
        when(clientResource.roles()).thenReturn(rolesResource);
        when(rolesResource.get("mock-role")).thenReturn(roleResource);
        when(realmResource.clients()).thenReturn(clientsResource);

        var clientRepresentation = mock(org.keycloak.representations.idm.ClientRepresentation.class);
        when(clientRepresentation.getId()).thenReturn("mock-client-id");

        when(clientsResource.findByClientId("lecosBurguer")).thenReturn(List.of(clientRepresentation));

        RoleRepresentation roleRepresentation = mock(RoleRepresentation.class);
        when(roleResource.toRepresentation()).thenReturn(roleRepresentation);

        var usersResource = mock(org.keycloak.admin.client.resource.UsersResource.class);
        var userResource = mock(org.keycloak.admin.client.resource.UserResource.class);
        var roleMappingResource = mock(org.keycloak.admin.client.resource.RoleMappingResource.class);

        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.get("12345")).thenReturn(userResource);
        when(userResource.roles()).thenReturn(roleMappingResource);

        when(realmResource.users()).thenReturn(usersResource);
        when(usersResource.create(any())).thenReturn(response);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastraUsuarioKeyCloak.cadastroUsuarioKeyCloak("username", "email", "lastName", "firstName", "mock-role", "password")
        );

        assertEquals(CP_0028 + " - []", exception.getMessage());
        verify(lcKeycloakErrorCadastroRepository, times(1)).insereUsuario(any(), any(), any(), any(), any(), any(), any());
    }
}