package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.service;

import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.requestDTO.requestCadastroDTO.RequestDTO;

public interface CadastroService {

    public void cadastro(RequestDTO requestDTO);

    public boolean isValidSecret(String secret);

    public boolean isValidCpf(String cpfCnpj);

    public boolean isValidCnpj(String cpfCnpj);



}
