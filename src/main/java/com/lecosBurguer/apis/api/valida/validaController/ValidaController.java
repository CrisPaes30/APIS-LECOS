package com.lecosBurguer.apis.api.valida.validaController;

import com.lecosBurguer.apis.api.valida.dto.TokenRequest;
import com.lecosBurguer.apis.api.valida.dto.TokenResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ValidaController {

    @PostMapping
    ResponseEntity<TokenResponseDTO> validarCadastro(@RequestBody TokenRequest request);

}
