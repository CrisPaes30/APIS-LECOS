package com.lecosBurguer.apis.utils;

import com.lecosBurguer.apis.exceptions.BusinessException;
import com.lecosBurguer.apis.repository.CadastroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.lecosBurguer.apis.enums.CadastroEnums.CP_0019;
import static com.lecosBurguer.apis.enums.CadastroEnums.CP_0020;

@Component
@RequiredArgsConstructor
public class ValidaEmail {

    private final CadastroRepository cadastroRepository;

    public void validaEmail(String email) {

        if (!email.contains("@")) {
            throw new BusinessException(CP_0019.getCode());
        }

        boolean exists = cadastroRepository.existsByEmail(email);

        if (exists) {
            throw new BusinessException(CP_0020.getCode());
        }
    }
}
