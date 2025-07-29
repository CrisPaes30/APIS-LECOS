package com.lecosBurguer.apis.api.valida.validaController.impl;

import com.lecosBurguer.apis.api.valida.dto.TokenRequest;
import com.lecosBurguer.apis.api.valida.dto.TokenResponseDTO;
import com.lecosBurguer.apis.api.valida.validaController.ValidaController;
import com.lecosBurguer.apis.entities.LcCadastro;
import com.lecosBurguer.apis.repository.CadastroRepository;
import com.lecosBurguer.apis.utils.TokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/usuarios/validar")
public class ValidaControllerImpl implements ValidaController {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private CadastroRepository cadastroRepository;


    @Operation(summary = "Validar email usuário")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Email validado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponseDTO.class),
                            examples = @ExampleObject(value = "{\"mensagem\": \"Cadastro confirmado com sucesso!\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Token inválido ou expirado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponseDTO.class),
                            examples = @ExampleObject(value = "{\"mensagem\": \"Token inválido ou expirado\"}")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Email não Encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponseDTO.class),
                            examples = @ExampleObject(value = "{\"mensagem\": \"Usuário não encontrado\"}")
                    )
            )
    })
    @Override
    public ResponseEntity<TokenResponseDTO> validarCadastro(TokenRequest request) {

        String token = request.getToken();

        if (!tokenUtil.validarToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new TokenResponseDTO("Token inválido ou expirado"));
        }

        String email = tokenUtil.extrairEmail(token);

        Optional<LcCadastro> mail = Optional.ofNullable(cadastroRepository.findByEmail(email));
        if (mail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new TokenResponseDTO("Usuário não encontrado"));
        }

        LcCadastro usuario = mail.get();
        usuario.setClienteAtivo('A');
        cadastroRepository.save(usuario);

        return ResponseEntity.ok( new TokenResponseDTO("Cadastro confirmado com sucesso!"));
    }
}
