package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.cadastro.impl;

import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.requestDTO.requestCadastroDTO.RequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserLoginController {

    @PostMapping
    public ResponseEntity<RequestDTO> loginUser(@RequestBody RequestDTO requestDTO);
}
