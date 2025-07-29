package com.lecosBurguer.apis.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class TokenUtil {

    @Value("${jwt.secret}")
    private String secret; // chave configurada no YAML

    private Key secretKey;

    private final long expirationMillis = 3600_000;

    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Gera um token JWT para validação de cadastro
     * @param email e-mail do usuário para o qual o token será gerado
     * @return String com o token JWT
     */
    public String gerarTokenValidacao(String email) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(email) // quem é o dono do token
                .setIssuedAt(agora)
                .setExpiration(expiracao)
                .claim("type", "VALIDACAO_CADASTRO")
                .signWith(secretKey)
                .compact();
    }

    /**
     * Valida um token JWT (verifica assinatura e expiração)
     * @param token o token JWT a ser validado
     * @return true se válido, false se inválido ou expirado
     */
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extrai o e-mail (subject) do token JWT
     * @param token o token JWT
     * @return String com o e-mail
     */
    public String extrairEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
