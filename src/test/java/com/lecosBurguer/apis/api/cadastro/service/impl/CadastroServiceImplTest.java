package com.lecosBurguer.apis.api.cadastro.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lecosBurguer.apis.api.cadastro.request.Items;
import com.lecosBurguer.apis.api.cadastro.request.RequestDTO;
import com.lecosBurguer.apis.api.cadastro.service.cadastroService.impl.CadastraUsuarioKeyCloakImpl;
import com.lecosBurguer.apis.api.cadastro.service.cadastroService.impl.CadastroServiceImpl;
import com.lecosBurguer.apis.api.cadastro.service.cadastroService.impl.EnviaEmailServiceImpl;
import com.lecosBurguer.apis.api.cadastro.utils.*;
import com.lecosBurguer.apis.entities.LcCadastro;
import com.lecosBurguer.apis.exceptions.BusinessException;
import com.lecosBurguer.apis.repository.CadastroRepository;
import com.lecosBurguer.apis.utils.ValidaCpfCnpj;
import com.lecosBurguer.apis.utils.ValidaEmail;
import com.lecosBurguer.apis.utils.ValidaSenhaCadastro;
import com.lecosBurguer.apis.utils.ValidaUsuarioExistente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;

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

    @Mock
    private ValidaEmail validaEmail;

    @Mock
    private EnviaEmailServiceImpl enviaEmailService;

    private static final String CP_0001 = "CP-0001";
    private static final String CP_0002 = "CP-0002";
    private static final String CP_0003 = "CP-0003";
    private static final String CP_0004 = "CP-0004";
    private static final String CP_0005 = "CP-0005";
    private static final String CP_0006 = "CP-0006";
    private static final String CP_0007 = "CP-0007";
    private static final String CP_0008 = "CP-0008";
    private static final String CP_0009 = "CP-0009";
    private static final String CP_0010 = "CP-0010";
    private static final String CP_0011 = "CP-0011";
    private static final String CP_0012 = "CP-0012";
    private static final String CP_0013 = "CP-0013";
    private static final String CP_0014 = "CP-0014";
    private static final String CP_0019 = "CP-0019";
    private static final String CP_0020 = "CP-0020";
    private static final String CP_0027 = "CP-0027";

    @Test
    void testDeveCadastrarComSucesso() throws JsonProcessingException {
        RequestDTO cadastro = RequestDTO.builder()
                .item(List.of(Items.builder()
                        .cadastro(CadastroStubBase
                                .cadastroBase()
                                .build())
                        .build())).build();
        LcCadastro lcCadastro = new LcCadastro();

        when(validaUsuarioExistente.validaUsuario(any())).thenReturn(true);
        when(cadastroRepository.save(any())).thenReturn(lcCadastro);

        cadastroServiceImpl.cadastro(cadastro);

        verify(cadastroRepository, times(1)).save(any());

    }

    @Test
    void testeDeveDarErroCP_0001QuandoArequestForNull() {

        RequestDTO cadastro = RequestDTO.builder()
                .item(List.of(Items.builder()
                        .cadastro(null)
                                .build())).build();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastro));

        assertEquals(CP_0001 + " - []", exception.getMessage());
    }

    @Test
    void testDeveDarErroCP_0002QuandoNaoExistirCadastroValido() {
        RequestDTO cadastro = RequestDTO.builder()
                .item(List.of())
                .build();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastro));

        assertEquals(CP_0002 + " - []", exception.getMessage());
    }

    @Test
    void testeDevedarErroCP_0003QuandoNomeForNulo() {

        CadastroUtilsError cadastroUtilsError = new CadastroUtilsError();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastroUtilsError.createResponseStub()));

        assertEquals(CP_0003 + " - []", exception.getMessage());
    }

    @Test
    void testeDevedarErroCP_004QuandoEmailForNulo() {

        RequestDTO cadastro = RequestDTO.builder()
                .item(List.of(Items.builder()
                        .cadastro(CadastroStubBase
                                .cadastroBase().email(null)
                                .build())
                        .build())).build();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastro));

        assertEquals(CP_0004 + " - []", exception.getMessage());
    }

    @Test
    void testeDevedarErroCP_005QuandoCpfCnpjForNulo() {

        RequestDTO cadastro = RequestDTO.builder()
                .item(List.of(Items.builder()
                        .cadastro(CadastroStubBase
                                .cadastroBase().cpfCnpj(null)
                                .build())
                        .build())).build();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastro));

        assertEquals(CP_0005 + " - []", exception.getMessage());
    }

    @Test
    void testeDevedarErroCP_006QuandoCpfCnpjJaCadastrado() {

        RequestDTO cadastro = RequestDTO.builder()
                .item(List.of(Items.builder()
                        .cadastro(CadastroStubBase
                                .cadastroBase()
                                .build())
                        .build())).build();

        when(cadastroRepository.existsByCpfCnpj(any())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastro));

        assertEquals(CP_0006 + " - []", exception.getMessage());
        verify(cadastroRepository, times(1)).existsByCpfCnpj(any());
    }

    @Test
    void testDeveDarErroCP_0007QuandoTelefoneForNulo() {

        RequestDTO cadastro = RequestDTO.builder()
                .item(List.of(Items.builder()
                        .cadastro(CadastroStubBase
                                .cadastroBase().telefone(null)
                                .build())
                        .build())).build();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastro));

        assertEquals(CP_0007 + " - []", exception.getMessage());
    }

    @Test
    void testDeveDarErroCP_0008QuandoCepForNulo() {

        CadastroUtilsCepError cadastroUtilsError = new CadastroUtilsCepError();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastroUtilsError.createResponseStub()));

        assertEquals(CP_0008 + " - []", exception.getMessage());
    }

    @Test
    void testDeveDarErroCP_0009QuandologradouroForNulo() {

        RequestDTO cadastro = RequestDTO.builder()
                .item(List.of(Items.builder()
                        .cadastro(CadastroStubBase
                                .cadastroBase().endereco(CadastroStubBase
                                        .enderecoBase()
                                        .logradouro(null)
                                        .build())
                                .build())
                        .build())).build();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastro));

        assertEquals(CP_0009 + " - []", exception.getMessage());


    }

    @Test
    void testDeveDarErroCP_0010QuandoBairroForNulo() {

        CadastroUtilsBairroError endereco = new CadastroUtilsBairroError();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(endereco.createResponseStub()));

        assertEquals(CP_0010 + " - []", exception.getMessage());

    }

    @Test
    void testDeveDarErroCP_0011QuandoNumeroForNulo() {

        RequestDTO cadastro = RequestDTO.builder()
                .item(List.of(Items.builder()
                        .cadastro(CadastroStubBase
                                .cadastroBase().endereco(CadastroStubBase
                                        .enderecoBase()
                                        .numero(null)
                                        .build())
                                .build())
                        .build())).build();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastro));

        assertEquals(CP_0011 + " - []", exception.getMessage());

    }

    @Test
    void testDeveDarErroCP_0012QuandoUfForNulo() {

        RequestDTO cadastro = RequestDTO.builder()
                .item(List.of(Items.builder()
                        .cadastro(CadastroStubBase
                                .cadastroBase().endereco(CadastroStubBase.enderecoBase().uf(null).build())
                                .build())
                        .build())).build();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastro));

        assertEquals(CP_0012 + " - []", exception.getMessage());

    }

    @Test
    void testDeveDarErroCP_0013QuandoUsuarioForNulo() {

        RequestDTO cadastro = RequestDTO.builder()
                .item(List.of(Items.builder()
                        .cadastro(CadastroStubBase
                                .cadastroBase().usuario(null)
                                .build())
                        .build())).build();

        when(validaUsuarioExistente.validaUsuario(any())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastro));

        assertEquals(CP_0013 + " - []", exception.getMessage());

    }

    @Test
    void testeDeveDarErroCP_0014QuandoAoSalvarRequest() {

        RequestDTO cadastro = RequestDTO.builder()
                .item(List.of(Items.builder()
                        .cadastro(CadastroStubBase
                                .cadastroBase()
                                .build())
                        .build())).build();

        when(validaUsuarioExistente.validaUsuario(any())).thenReturn(true);
        when(cadastroRepository.save(any())).thenThrow(new BusinessException(CP_0014, "Erro ao salvar"));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastro));

        assertEquals(CP_0014, exception.getCode());
    }



    @Test
    void testDeveValidarSenhaForte() throws JsonProcessingException {

        RequestDTO cadastro = RequestDTO.builder()
                .item(List.of(Items.builder()
                        .cadastro(CadastroStubBase
                                .cadastroBase()
                                .build())
                        .build())).build();

        when(validaUsuarioExistente.validaUsuario(any())).thenReturn(true);
        when(validaSenhaCadastro.validaSecretForte(any())).thenReturn(true);
        when(cadastroRepository.save(any())).thenReturn(new LcCadastro());

        cadastroServiceImpl.cadastro(cadastro);

        verify(validaUsuarioExistente, times(1)).validaUsuario(any());
        verify(validaSenhaCadastro, times(1)).validaSecretForte(any());
        verify(cadastroRepository, times(1)).save(any());
    }


    @Test
    void testDeveValidarSeUsuarioJaCadastrado() {

        RequestDTO cadastro = RequestDTO.builder()
                .item(List.of(Items.builder()
                        .cadastro(CadastroStubBase
                                .cadastroBase()
                                .build())
                        .build())).build();

        BusinessException businessException = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastro));

        assertEquals(CP_0027, businessException.getCode());
    }

    @Test
    void testDeveDarSucessoQuandoNotificaoforFalse() throws JsonProcessingException {

        RequestDTO cadastro = RequestDTO.builder()
                .item(List.of(Items.builder()
                        .cadastro(CadastroStubBase
                                .cadastroBase().indNotificacao(false)
                                .build())
                        .build())).build();

        when(validaUsuarioExistente.validaUsuario(any())).thenReturn(true);
        when(validaSenhaCadastro.validaSecretForte(any())).thenReturn(true);
        when(cadastroRepository.save(any())).thenReturn(new LcCadastro());

        cadastroServiceImpl.cadastro(cadastro);

        verify(validaUsuarioExistente, times(1)).validaUsuario(any());
        verify(validaSenhaCadastro, times(1)).validaSecretForte(any());
        verify(cadastroRepository, times(1)).save(any());
    }


}