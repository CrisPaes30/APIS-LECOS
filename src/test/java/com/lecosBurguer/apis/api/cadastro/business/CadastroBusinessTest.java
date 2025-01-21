package com.lecosBurguer.apis.api.cadastro.business;


import com.lecosBurguer.apis.api.cadastro.request.Cadastro;
import com.lecosBurguer.apis.api.cadastro.request.Endereco;
import com.lecosBurguer.apis.api.cadastro.request.Items;
import com.lecosBurguer.apis.api.cadastro.request.RequestDTO;
import com.lecosBurguer.apis.api.cadastro.service.impl.CadastroServiceImpl;
import com.lecosBurguer.apis.utils.MensagemResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastroBusinessTest {

    @InjectMocks
    private CadastroBussines cadastroBussines;

    @Mock
    private CadastroServiceImpl cadastroService;

    @Mock
    private ReloadableResourceBundleMessageSource messageSource;

    @Mock
    private MensagemResolver resolver;

    private String message;


    @Test
    void createResponse() {

        doNothing().when(cadastroService).cadastro(any(RequestDTO.class));

        cadastroBussines.createResponse(createResponseStub());

        verify(cadastroService, times(1)).cadastro(any(RequestDTO.class));
    }

    public RequestDTO createResponseStub() {
        RequestDTO requestDTO = new RequestDTO();

        requestDTO.setItem(listItemStub());

        return requestDTO;
    }

    public List<Items> listItemStub() {

        List<Items> items = new ArrayList<>();
        Items item = new Items();

        item.setCadastro(cadastroStub());

        items.add(item);

        return items;
    }

    public Cadastro cadastroStub() {
        Cadastro cadastro = new Cadastro();

        cadastro.setNome("Leandro");
        cadastro.setSobrenome("Silva");
        cadastro.setEmail("3hM7f@example.com");
        cadastro.setTelefone("11999999999");
        cadastro.setCpfCnpj("99999999999");
        cadastro.setIndNotificacao("S");
        cadastro.setUsuario("leandro");
        cadastro.setSenha("123456");
        cadastro.setEndereco(enderecoStub());

        return cadastro;
    }

    public Endereco enderecoStub() {
        Endereco endereco = new Endereco();

        endereco.setCep("99999999");
        endereco.setLogradouro("rua 1");
        endereco.setBairro("bairro 1");
        endereco.setNumero("1");
        endereco.setComplemento("complemento 1");
        endereco.setUf("SP");

        return endereco;
    }
}