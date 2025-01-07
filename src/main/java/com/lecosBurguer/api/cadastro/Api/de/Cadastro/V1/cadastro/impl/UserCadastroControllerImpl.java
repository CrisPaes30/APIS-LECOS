package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.cadastro.impl;

import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.requestDTO.requestCadastroDTO.RequestDTO;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.response.respondeDTO.ResponseDTO;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.response.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/user-registration")
public class UserCadastroControllerImpl implements UserCadastroController {

    private final ResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<ResponseDTO> cadastro(RequestDTO requestDTO) {
        ResponseDTO responseDTO = responseBuilder.createResponse(requestDTO);

        return new ResponseEntity<>(responseDTO, hasError(responseDTO) ? HttpStatus.MULTI_STATUS : HttpStatus.CREATED);
    }

    private boolean hasError(ResponseDTO responseBuilder) {
        return responseBuilder.getData().getItems().stream().anyMatch(item -> item.getError() != null);
    }
}
