package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.cadastro.impl;

import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.dto.RequestDTO;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CadastroController {

    @PostMapping
    ResponseEntity<Response> cadastro(@RequestBody RequestDTO requestDTO);
}
