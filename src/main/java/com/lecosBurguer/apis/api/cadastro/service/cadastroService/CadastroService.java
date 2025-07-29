package com.lecosBurguer.apis.api.cadastro.service.cadastroService;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.lecosBurguer.apis.api.cadastro.request.RequestDTO;

public interface CadastroService {

    public void cadastro(RequestDTO requestDTO) throws JsonProcessingException;

}
