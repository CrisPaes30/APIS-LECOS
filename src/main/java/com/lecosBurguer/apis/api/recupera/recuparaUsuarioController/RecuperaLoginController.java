package com.lecosBurguer.apis.api.recupera.recuparaUsuarioController;

import com.lecosBurguer.apis.api.recupera.request.RecoveryDTO;
import com.lecosBurguer.apis.api.response.ResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface RecuperaLoginController {

    @PutMapping
    public ResponseEntity<ResponseDTO> recupera(@Valid @RequestBody RecoveryDTO recoveryDTO);
}
