package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.service.impl;


import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.dto.RequestDTO;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.entities.LcCadastro;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.exceptions.BusinessException;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.repository.CadastroRepository;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.service.CadastroService;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.enums.CadastroEnums.*;

@Service
@Data
public class CadastroServiceImpl implements CadastroService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final CadastroRepository cadastroRepository;

    @Override
    public void cadastro(RequestDTO requestDTO) {

        var cadastroData = requestDTO.getItem().stream()
                .map(innerItem -> {
                    if (innerItem.getCadastro() == null) {
                        throw new BusinessException(CP_0001.getCode());
                    }
                    return innerItem.getCadastro();
                })
                .findFirst()
                .orElseThrow(() -> new BusinessException(CP_0002.getCode()));

        LcCadastro lcCadastro = new LcCadastro();
        lcCadastro.setNome(getValidField(cadastroData.getNome(), CP_0003.getCode()));
        lcCadastro.setEmail(getValidField(cadastroData.getEmail(), CP_0004.getCode()));

        String cpfCnpj = getValidField(cadastroData.getCpfCnpj(), CP_0005.getCode());
        if (isCpfCnpjCadastrado(cpfCnpj)) {
            throw new BusinessException(CP_0006.getCode());
        }

        isValidCpfCnpj(cpfCnpj);
        lcCadastro.setCpfCnpj(cpfCnpj);

        lcCadastro.setTelefone(getValidField(cadastroData.getTelefone(), CP_0007.getCode()));
        lcCadastro.setCep(getValidField(cadastroData.getEndereco().getCep(), CP_0008.getCode()));
        lcCadastro.setLogradouro(getValidField(cadastroData.getEndereco().getLogradouro(), CP_0009.getCode()));
        lcCadastro.setBairro(getValidField(cadastroData.getEndereco().getBairro(), CP_0010.getCode()));
        lcCadastro.setNumero(getValidField(cadastroData.getEndereco().getNumero(), CP_0011.getCode()));
        lcCadastro.setComplemento(cadastroData.getEndereco().getComplemento());
        lcCadastro.setUf(getValidField(cadastroData.getEndereco().getUf(), CP_0012.getCode()));
        lcCadastro.setClient(getValidField(cadastroData.getUser(), CP_0013.getCode()));
        lcCadastro.setIndNotificacao(cadastroData.getIndNotificacao());

        validaSecretForte(cadastroData.getSecret(), lcCadastro);

        try {
            cadastroRepository.save(lcCadastro);
        } catch (Exception e) {
            throw new BusinessException(CP_0014.getCode());
        }
    }

    private String getValidField(String field, String errorMessage) {
        if (field == null || field.isEmpty()) {
            throw new BusinessException(errorMessage);
        }
        return field;
    }

    private void isValidCpfCnpj(String cpfCnpj) {
        if (cpfCnpj.length() == 11 && !isValidCpf(cpfCnpj)) {
            throw new BusinessException(CP_0015.getCode());
        } else if (cpfCnpj.length() != 11 && !isValidCnpj(cpfCnpj)) {
            throw new BusinessException(CP_0016.getCode());
        }
    }

    private void validaSecretForte(String secret, LcCadastro lcCadastro) {
        if (!isValidSecret(secret)) {
            throw new BusinessException(
                    CP_0017.getCode()
            );
        }
        lcCadastro.setSecret(passwordEncoder.encode(secret));
    }

    @Override
    public boolean isValidSecret(String secret) {
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

    @Override
    public boolean isValidCpf(String cpf) {
        cpf = cpf.replaceAll("\\D", ""); // Remove não dígitos
        if (cpf.length() != 11) return false;

        int[] weights1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        return validateDocument(cpf, weights1, weights2);
    }

    @Override
    public boolean isValidCnpj(String cnpj) {
        cnpj = cnpj.replaceAll("\\D", ""); // Remove não dígitos
        if (cnpj.length() != 14) return false;

        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        return validateDocument(cnpj, weights1, weights2);
    }

    private boolean validateDocument(String doc, int[] weights1, int[] weights2) {
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

    private boolean isCpfCnpjCadastrado(String cpfCnpj) {
        return cadastroRepository.existsByCpfCnpj(cpfCnpj);
    }
}
