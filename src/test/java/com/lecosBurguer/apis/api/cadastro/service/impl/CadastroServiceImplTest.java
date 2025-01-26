package com.lecosBurguer.apis.api.cadastro.service.impl;

import com.lecosBurguer.apis.api.cadastro.request.Cadastro;
import com.lecosBurguer.apis.api.cadastro.request.Items;
import com.lecosBurguer.apis.api.cadastro.request.RequestDTO;
import com.lecosBurguer.apis.api.cadastro.service.cadastroService.CadastroService;
import com.lecosBurguer.apis.api.cadastro.utils.CadastroUtilsError;
import com.lecosBurguer.apis.api.cadastro.utils.CadastroUtilsErrorEmail;
import com.lecosBurguer.apis.api.cadastro.utils.CadastroUtilsSucesso;
import com.lecosBurguer.apis.api.response.Item;
import com.lecosBurguer.apis.entities.LcCadastro;
import com.lecosBurguer.apis.exceptions.BusinessException;
import com.lecosBurguer.apis.repository.CadastroRepository;
import com.lecosBurguer.apis.utils.ValidaCpfCnpj;
import com.lecosBurguer.apis.utils.ValidaSenhaCadastro;
import com.lecosBurguer.apis.utils.ValidaUsuarioExistente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

import static com.lecosBurguer.apis.enums.CadastroEnums.CP_0002;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastroServiceImplTest {

    @InjectMocks
    private CadastroServiceImpl cadastroServiceImpl;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private CadastroRepository cadastroRepository;

    @Mock
    private ValidaCpfCnpj validaCpfCnpj;

    @Mock
    private ValidaSenhaCadastro validaSenhaCadastro;

    @Mock
    private CadastraUsuarioKeyCloakImpl cadastroUsuarioKeyCloak;

    @Mock
    private ValidaUsuarioExistente validaUsuarioExistente;

    private static final String CP_0001 = "CP-0001";
    private static final String CP_0002 = "CP-0002";
    private static final String CP_0003 = "CP-0003";
    private static final String CP_0004 = "CP-0004";

    @Test
    void testDeveCadastrarComSucesso() {
        CadastroUtilsSucesso cadastroUtilsSucesso = new CadastroUtilsSucesso();
        LcCadastro lcCadastro = new LcCadastro();

        when(validaUsuarioExistente.validaUsuario(any())).thenReturn(true);
        when(cadastroRepository.save(any())).thenReturn(lcCadastro);

        cadastroServiceImpl.cadastro(cadastroUtilsSucesso.createResponseStub());

        verify(cadastroRepository, times(1)).save(any());

    }

    @Test
    void testeDeveDarErroCP_0001QuandoArequestForNull() {

        Items itemDTO = new Items();
        itemDTO.setCadastro(null);

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setItem(Collections.singletonList(itemDTO));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(requestDTO));

        assertEquals(CP_0001 + " - []", exception.getMessage());
    }

    @Test
    void testDeveDarErroCP_0002QuandoArequestForNull() {

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setItem(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(requestDTO));

        assertEquals(CP_0002 + " - []", exception.getMessage());
    }

    @Test
    void testeDevedarErroCP_003QuandoNomeForNulo() {

        CadastroUtilsError cadastroUtilsError = new CadastroUtilsError();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastroUtilsError.createResponseStub()));

        assertEquals(CP_0003 + " - []", exception.getMessage());
    }

    @Test
    void testeDevedarErroCP_004QuandoEmailForNulo() {

        CadastroUtilsErrorEmail cadastroUtilsError = new CadastroUtilsErrorEmail();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastroUtilsError.createResponseStub()));

        assertEquals(CP_0004 + " - []", exception.getMessage());
    }

    @Test
    void testDeveValidarSeUsuarioJaCadastrado() {
        CadastroUtilsSucesso cadastroUtilsSucesso = new CadastroUtilsSucesso();

        BusinessException businessException = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastroUtilsSucesso.createResponseStub()));

        assertEquals("CP-0027", businessException.getCode());
    }



}