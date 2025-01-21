package com.lecosBurguer.apis.api.usuario.service.impl;

import com.lecosBurguer.apis.api.usuario.service.userService.UserService;
import com.lecosBurguer.apis.entities.LcCadastro;
import com.lecosBurguer.apis.exceptions.BusinessException;
import com.lecosBurguer.apis.repository.CadastroRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.lecosBurguer.apis.enums.CadastroEnums.*;

@Service
@Slf4j
@Data
public class UserServiceImpl implements UserService {

    private final CadastroRepository cadastroRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void authenticate(String usernameOrEmail, String password) {
        if (usernameOrEmail == null || usernameOrEmail.isEmpty()) {
            throw new BusinessException(CP_0021.getCode());
        }

        if (password == null || password.isEmpty()) {
            throw new BusinessException(CP_0022.getCode());
        }

        LcCadastro user = null;

        try {
            if (usernameOrEmail.contains("@")) {
                user = cadastroRepository.findByEmail(usernameOrEmail);
            } else {
                user = cadastroRepository.findByClient(usernameOrEmail);
            }
        } catch (Exception e) {
            log.error("Erro ao buscar usuário no banco de dados: {}", e.getMessage());
            throw new BusinessException(CP_0023.getCode());
        }

        if (user == null || user.getSecret() == null) {
            throw new BusinessException(CP_0024.getCode());
        }

        if (!passwordEncoder.matches(password, user.getSecret())) {
            throw new BusinessException(CP_0025.getCode());
        }

        log.info("Usuário autenticado com sucesso: {}", user.getNome());
    }
}
