package com.lecosBurguer.apis.utils;

import com.lecosBurguer.apis.repository.CadastroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ValidaUsuarioExistente {

    private final CadastroRepository cadastroRepository;

    public Boolean validaUsuario(String usuario){

        try {
            boolean isExiste = cadastroRepository.existsByClient(usuario);

            return !isExiste;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
