package com.lecosBurguer.apis.api.recupera.impl;

import com.lecosBurguer.apis.api.recupera.recuparaUsuarioController.RecuperaLoginController;
import com.lecosBurguer.apis.api.recupera.request.RecoveryDTO;
import com.lecosBurguer.apis.api.response.ResponseDTO;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/user-recovery")
@RolesAllowed("cliente")
public class RecuperaLoginControllerImpl implements RecuperaLoginController {


    @Override
    public ResponseEntity<ResponseDTO> recupera(RecoveryDTO recoveryDTO) {
        return null;
    }
}
