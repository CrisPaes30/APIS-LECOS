package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.cadastro.impl;

import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.requestDTO.requestCadastroDTO.RequestDTO;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.response.respondeDTO.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserCadastroController {

    @PostMapping
    ResponseEntity<ResponseDTO> cadastro(@RequestBody RequestDTO requestDTO);
}
