//package com.lecosBurguer.apis.api.usuario.usuarioController.impl;
//
//import com.lecosBurguer.apis.api.response.ResponseDTO;
//import com.lecosBurguer.apis.api.usuario.request.RequestLoginDTO;
//import com.lecosBurguer.apis.api.usuario.business.UsuarioBusiness;
//import com.lecosBurguer.apis.api.usuario.usuarioController.usuarioController.UserLoginController;
//import jakarta.annotation.security.RolesAllowed;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping(value = "/v1/login-user")
//@RolesAllowed("cliente")
//public class UserLoginControllerImpl implements UserLoginController {
//
//    private final UsuarioBusiness usuarioBussines;
//
//    @Override
//    public ResponseEntity loginUser(RequestLoginDTO requestLoginDTO) {
//        try {
//            ResponseDTO responseDTO = usuarioBussines.createResponseBuilder(requestLoginDTO);
//
//            HttpStatus status = determinaHttpStatus(responseDTO);
//            return new ResponseEntity<>(responseDTO, status);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDTO());
//        }
//    }
//
//    /**
//     * Determina o status HTTP com base no conteúdo do ResponseDTO.
//     */
//    private HttpStatus determinaHttpStatus(ResponseDTO responseDTO) {
//
//        return hasError(responseDTO) ? HttpStatus.MULTI_STATUS : HttpStatus.OK;
//    }
//
//    private boolean hasError(ResponseDTO responseBuilder) {
//        return responseBuilder.getData().getItems().stream().anyMatch(item -> item.getError() != null);
//    }
//}
