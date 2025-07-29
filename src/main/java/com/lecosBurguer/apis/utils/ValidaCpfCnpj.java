package com.lecosBurguer.apis.utils;

import com.lecosBurguer.apis.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import static com.lecosBurguer.apis.enums.CadastroEnums.CP_0015;
import static com.lecosBurguer.apis.enums.CadastroEnums.CP_0016;

@Component
public class ValidaCpfCnpj {

    public void isValidCpfCnpj(String cpfCnpj) {
        boolean isCpfCnpj = isValidCpf(cpfCnpj) || isValidCnpj(cpfCnpj);
        String cleanCpfCnpj = removeCaracteresEspeciais(cpfCnpj);
        if (cleanCpfCnpj.length() == 11 && !isCpfCnpj) {
            throw new BusinessException(CP_0015.getCode());
        } else if (cleanCpfCnpj.length() > 11 && !isCpfCnpj) {
            throw new BusinessException(CP_0016.getCode());
        }
    }

    private boolean isValidCpf(String cpf) {
        cpf = removeCaracteresEspeciais(cpf);
        if (cpf.length() != 11) return false;

        if (cpf.chars().distinct().count() == 1) return false;

        int[] weights1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        return validateDocumento(cpf, weights1, weights2);
    }

    private boolean isValidCnpj(String cnpj) {
        cnpj = removeCaracteresEspeciais(cnpj);
        if (cnpj.length() != 14) return false;

        if (cnpj.chars().distinct().count() == 1) return false;

        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        return validateDocumento(cnpj, weights1, weights2);
    }

    private String removeCaracteresEspeciais(String value) {
        return value.replaceAll("\\D", "");
    }

    private boolean validateDocumento(String doc, int[] weights1, int[] weights2) {
        int d1 = calculateChecksum(doc, weights1);
        int d2 = calculateChecksum(doc, weights2);

        return doc.equals(doc.substring(0, doc.length() - 2) + d1 + d2);
    }

    private int calculateChecksum(String doc, int[] weights) {
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += (doc.charAt(i) - '0') * weights[i];
        }
        int mod = sum % 11;
        return (mod < 2) ? 0 : 11 - mod;
    }
}
