package com.lecosBurguer.apis.api.cadastro.service.cadastroService;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CadastraUsuariokeyCloak {

    void cadastroUsuarioKeyCloak(String username, String email, String lastName, String firstName, String roles, String password) throws JsonProcessingException;
}
