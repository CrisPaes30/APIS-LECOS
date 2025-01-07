package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.cadastro.impl;

import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.requestDTO.requestCadastroDTO.RequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/login-user")
public class UserLoginControllerImpl implements UserLoginController {

    @Override
    public ResponseEntity loginUser(RequestDTO requestDTO) {
        return null;
    }
}
