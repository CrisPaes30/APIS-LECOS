package com.lecosBurguer.apis.api.cadastro.service.cadastroService.impl;

import com.lecosBurguer.apis.api.cadastro.service.cadastroService.EnviaEmailService;
import com.lecosBurguer.apis.token.brocker.EnviaEmailCadastro;
import com.lecosBurguer.apis.token.brocker.KeycloakService;
import com.lecosBurguer.apis.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnviaEmailServiceImpl implements EnviaEmailService {

    private final KeycloakService keycloakService;
    private final EnviaEmailCadastro enviaEmailCadastro;

    @Autowired
    private final TokenUtil tokenUtil;

    @Override
    public void enviaEmail(String email) {

        String token = keycloakService.getServiceToken();
        String bearerToken = "Bearer " + token;

        String tokenGerado = this.tokenUtil.gerarTokenValidacao(email);

        Map<String, Object> body = Map.of(
                "email", email,
                "token", tokenGerado

        );
        enviaEmailCadastro.enviaEmail(bearerToken,body);
    }
}
