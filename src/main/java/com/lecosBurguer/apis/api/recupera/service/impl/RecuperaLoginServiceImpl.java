package com.lecosBurguer.apis.api.recupera.service.impl;

import com.lecosBurguer.apis.api.recupera.service.service.RecuperaLoginService;
import com.lecosBurguer.apis.exceptions.BusinessException;
import com.lecosBurguer.apis.repository.CadastroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecuperaLoginServiceImpl implements RecuperaLoginService {

    private final CadastroRepository cadastroRepository;

    @Override
    public String recoveryUsuario(String email) {

        if (email.isEmpty()) {
            throw new BusinessException("email cannot be empty");
        }

        boolean emailExists;

        try {
          emailExists = cadastroRepository.existsByEmail(email);
        } catch (BusinessException e) {
            throw new BusinessException("email not found");
        }

        if(emailExists) {
            return email;
        }else {
            throw new BusinessException("email not found");
        }

    }
}
