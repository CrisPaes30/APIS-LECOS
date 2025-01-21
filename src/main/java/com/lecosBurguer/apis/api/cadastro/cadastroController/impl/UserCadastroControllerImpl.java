package com.lecosBurguer.apis.api.cadastro.cadastroController.impl;

import com.lecosBurguer.apis.api.cadastro.business.CadastroBussines;
import com.lecosBurguer.apis.api.cadastro.cadastroController.controller.UserCadastroController;
import com.lecosBurguer.apis.api.cadastro.request.RequestDTO;
import com.lecosBurguer.apis.api.response.ResponseDTO;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/user-registration")
@PreAuthorize("hasRole('cliente')")
public class UserCadastroControllerImpl implements UserCadastroController {

    private final CadastroBussines cadastroBussines;

    @Override
    public ResponseEntity<ResponseDTO> cadastro(RequestDTO requestDTO) {

        ResponseDTO responseDTO = cadastroBussines.createResponse(requestDTO);

        return new ResponseEntity<>(responseDTO, hasError(responseDTO) ? HttpStatus.MULTI_STATUS : HttpStatus.CREATED);
    }

    private boolean hasError(ResponseDTO responseBuilder) {
        return responseBuilder.getData().getItems().stream().anyMatch(item -> item.getError() != null);
    }
}
