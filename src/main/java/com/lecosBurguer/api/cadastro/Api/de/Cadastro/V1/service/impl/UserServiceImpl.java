package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.service.impl;

import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.entities.LcCadastro;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.exceptions.BusinessException;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.repository.CadastroRepository;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final CadastroRepository cadastroRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void authenticate(String usernameOrEmail, String password) {
        if (usernameOrEmail == null || usernameOrEmail.isEmpty()) {
            throw new BusinessException("Username ou email não pode ser nulo ou vazio.");
        }

        if (password == null || password.isEmpty()) {
            throw new BusinessException("Campo senha não pode ser nulo ou vazio.");
        }

        LcCadastro user = null;

        try {
            if (usernameOrEmail.contains("@")) {
                user = cadastroRepository.findByEmail(usernameOrEmail);
            } else {
                user = cadastroRepository.findBynome(usernameOrEmail);
            }
        } catch (Exception e) {
            log.error("Erro ao buscar usuário no banco de dados: {}", e.getMessage());
            throw new BusinessException("Erro interno ao tentar autenticar o usuário.");
        }

        if (user == null || user.getSecret() == null) {
            throw new BusinessException("Usuário ou senha inválidos.");
        }

        if (!passwordEncoder.matches(password, user.getSecret())) {
            throw new BusinessException("Senha inválida.");
        }

        log.info("Usuário autenticado com sucesso: {}", user.getNome());
    }
}
