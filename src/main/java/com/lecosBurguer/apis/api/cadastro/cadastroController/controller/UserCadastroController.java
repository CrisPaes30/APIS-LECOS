package com.lecosBurguer.apis.api.cadastro.cadastroController.controller;

import com.lecosBurguer.apis.api.cadastro.request.RequestDTO;
import com.lecosBurguer.apis.api.response.ResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserCadastroController {

    @PostMapping
    ResponseEntity<ResponseDTO> cadastro(@Valid @RequestBody RequestDTO requestDTO);
}
