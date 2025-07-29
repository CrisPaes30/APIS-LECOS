package com.lecosBurguer.apis.api.cadastro.service.cadastroService.impl;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.lecosBurguer.apis.api.cadastro.request.RequestDTO;
import com.lecosBurguer.apis.api.cadastro.service.cadastroService.CadastroService;
import com.lecosBurguer.apis.entities.LcCadastro;
import com.lecosBurguer.apis.exceptions.BusinessException;
import com.lecosBurguer.apis.repository.CadastroRepository;
import com.lecosBurguer.apis.utils.ValidaCpfCnpj;
import com.lecosBurguer.apis.utils.ValidaEmail;
import com.lecosBurguer.apis.utils.ValidaSenhaCadastro;
import com.lecosBurguer.apis.utils.ValidaUsuarioExistente;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.lecosBurguer.apis.enums.CadastroEnums.*;


@Service
@Data
@Slf4j
public class CadastroServiceImpl implements CadastroService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final CadastroRepository cadastroRepository;

    private final ValidaCpfCnpj validaCpfCnpj;

    private final ValidaSenhaCadastro validaSenhaCadastro;

    private final CadastraUsuarioKeyCloakImpl cadastroUsuarioKeyCloak;

    private final ValidaUsuarioExistente validaUsuarioExistente;

    private final ValidaEmail validaEmail;

    private final EnviaEmailServiceImpl enviaEmailService;

    @Transactional
    @Override
    public void cadastro(RequestDTO requestDTO) throws JsonProcessingException {
        log.info("Realizando cadastro");

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

        String email = getValidField(
                cadastroData.getEmail() != null ? cadastroData.getEmail().toLowerCase() : null,
                CP_0004.getCode()
        );

        validaEmail.validaEmail(email);

        lcCadastro.setEmail(email);

        String cpfCnpj = getValidField(cadastroData.getCpfCnpj(), CP_0005.getCode());
        if (isCpfCnpjCadastrado(cpfCnpj)) {
            throw new BusinessException(CP_0006.getCode());
        }

        validaCpfCnpj.isValidCpfCnpj(cpfCnpj);
        lcCadastro.setCpfCnpj(cpfCnpj);

        lcCadastro.setTelefone(getValidField(cadastroData.getTelefone(), CP_0007.getCode()));
        lcCadastro.setCep(getValidField(cadastroData.getEndereco().getCep(), CP_0008.getCode()));
        lcCadastro.setLogradouro(getValidField(cadastroData.getEndereco().getLogradouro(), CP_0009.getCode()));
        lcCadastro.setBairro(getValidField(cadastroData.getEndereco().getBairro(), CP_0010.getCode()));
        lcCadastro.setNumero(getValidField(cadastroData.getEndereco().getNumero(), CP_0011.getCode()));
        lcCadastro.setComplemento(cadastroData.getEndereco().getComplemento());
        lcCadastro.setUf(getValidField(cadastroData.getEndereco().getUf(), CP_0012.getCode()));

        if (validaUsuarioExistente.validaUsuario(cadastroData.getUsuario())) {
            lcCadastro.setClient(getValidField(cadastroData.getUsuario(), CP_0013.getCode()));

        } else {
            throw new BusinessException(CP_0027.getCode());
        }

        String notificacao = validaNotificacao(cadastroData.getIndNotificacao());

        lcCadastro.setIndNotificacao(notificacao);

        boolean isValidSecret = validaSenhaCadastro.validaSecretForte(cadastroData.getSenha());

        if (isValidSecret) {
            lcCadastro.setSecret(passwordEncoder.encode(cadastroData.getSenha()));
        }

        lcCadastro.setClienteAtivo('N');

        try {
            cadastroRepository.save(lcCadastro);
        } catch (Exception e) {
            throw new BusinessException(CP_0014.getCode());
        }

        log.info("Cadastro realizado com sucesso");

        cadastroUsuarioKeyCloak.cadastroUsuarioKeyCloak(requestDTO.getItem().get(0).getCadastro().getUsuario(),
                requestDTO.getItem().get(0).getCadastro().getEmail(), requestDTO.getItem().get(0).getCadastro().getSobrenome(),
                requestDTO.getItem().get(0).getCadastro().getNome(), "cliente", requestDTO.getItem().get(0).getCadastro().getSenha());

        enviaEmailService.enviaEmail(requestDTO.getItem().get(0).getCadastro().getEmail());
    }

    private String getValidField(String field, String errorMessage) {
        if (field == null || field.isEmpty()) {
            throw new BusinessException(errorMessage);
        }
        return field;
    }

    private boolean isCpfCnpjCadastrado(String cpfCnpj) {
        return cadastroRepository.existsByCpfCnpj(cpfCnpj);
    }

    private String validaNotificacao(Boolean notificacao) {

        if (notificacao) {
            return "S";
        } else {
            return "N";
        }
    }
}
