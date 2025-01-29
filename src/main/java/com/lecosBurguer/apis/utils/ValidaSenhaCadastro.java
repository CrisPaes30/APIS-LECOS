package com.lecosBurguer.apis.utils;

import com.lecosBurguer.apis.entities.LcCadastro;
import com.lecosBurguer.apis.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import static com.lecosBurguer.apis.enums.CadastroEnums.CP_0017;

@Component
public class ValidaSenhaCadastro {

    public boolean validaSecretForte(String secret) {
        if (!isValidSecret(secret)) {
            throw new BusinessException(
                    CP_0017.getCode()
            );
        }
        return true;
    }

    private boolean isValidSecret(String secret) {
        if (secret == null || secret.length() < 6) return false;

        boolean achouNumero = false, achouMaiuscula = false, achouMinuscula = false, achouSimbolo = false;

        for (char c : secret.toCharArray()) {
            if (Character.isDigit(c)) achouNumero = true;
            else if (Character.isUpperCase(c)) achouMaiuscula = true;
            else if (Character.isLowerCase(c)) achouMinuscula = true;
            else achouSimbolo = true;
        }

        return achouNumero && achouMaiuscula && achouMinuscula && achouSimbolo;
    }



}
