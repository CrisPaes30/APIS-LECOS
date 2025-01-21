package com.lecosBurguer.apis.api.usuario.usuarioController.usuarioController;

import com.lecosBurguer.apis.api.usuario.request.RequestLoginDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserLoginController {

    @PostMapping
    public ResponseEntity<RequestLoginDTO> loginUser(@Valid @RequestBody RequestLoginDTO requestLoginDTO);
}
