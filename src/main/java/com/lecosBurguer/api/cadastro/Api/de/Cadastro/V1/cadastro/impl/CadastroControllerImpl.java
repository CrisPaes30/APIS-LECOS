package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.cadastro.impl;

import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.dto.RequestDTO;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.response.Response;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.response.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/user-registration")
public class CadastroControllerImpl implements CadastroController{

    //private final CadastroService cadastroService;

    private final ResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<Response> cadastro(RequestDTO requestDTO) {
       // ResponseBuilder builder = new ResponseBuilder();
        Response response = responseBuilder.createResponse(requestDTO);
        //cadastroService.cadastro(requestDTO);

        return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(response);
    }
}
