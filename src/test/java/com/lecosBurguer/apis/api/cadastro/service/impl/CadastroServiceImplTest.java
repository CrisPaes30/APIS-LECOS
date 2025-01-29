package com.lecosBurguer.apis.api.cadastro.service.impl;

import com.lecosBurguer.apis.api.cadastro.request.Cadastro;
import com.lecosBurguer.apis.api.cadastro.request.Endereco;
import com.lecosBurguer.apis.api.cadastro.request.Items;
import com.lecosBurguer.apis.api.cadastro.request.RequestDTO;
import com.lecosBurguer.apis.api.cadastro.service.cadastroService.CadastroService;
import com.lecosBurguer.apis.api.cadastro.utils.*;
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
    void testeDevedarErroCP_0003QuandoNomeForNulo() {

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
    void testeDevedarErroCP_005QuandoCpfCnpjForNulo() {

        CadastroUtilsErrorCpfCnpjError cadastroUtilsErrorCpfCnpjError = new CadastroUtilsErrorCpfCnpjError();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastroUtilsErrorCpfCnpjError.createResponseStub()));

        assertEquals(CP_0005 + " - []", exception.getMessage());
    }

    @Test
    void testeDevedarErroCP_006QuandoCpfCnpjJaCadastrado() {

        CadastroUtilsErrorCpfCnpjJaExiste cadastroUtilsErrorCpfCnpjError = new CadastroUtilsErrorCpfCnpjJaExiste();

        when(cadastroRepository.existsByCpfCnpj(any())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastroUtilsErrorCpfCnpjError.createResponseStub()));

        assertEquals(CP_0006 + " - []", exception.getMessage());
        verify(cadastroRepository, times(1)).existsByCpfCnpj(any());
    }

    @Test
    void testDeveDarErroCP_0007QuandoTelefoneForNulo() {

        CadastroUtilsTelefoneError cadastroUtilsError = new CadastroUtilsTelefoneError();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastroUtilsError.createResponseStub()));

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

        CadastroUtilsLogradouroError endereco = new CadastroUtilsLogradouroError();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(endereco.createResponseStub()));

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

        CadastroUtilsNumeroError endereco = new CadastroUtilsNumeroError();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(endereco.createResponseStub()));

        assertEquals(CP_0011 + " - []", exception.getMessage());

    }

    @Test
    void testDeveDarErroCP_0012QuandoEnderecoForNulo() {

        CadastroUtilsUfError endereco = new CadastroUtilsUfError();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(endereco.createResponseStub()));

        assertEquals(CP_0012 + " - []", exception.getMessage());

    }

    @Test
    void testDeveDarErroCP_0013QuandoUsuarioForNulo() {

        CadastroUtilsUsuarioError endereco = new CadastroUtilsUsuarioError();

        when(validaUsuarioExistente.validaUsuario(any())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(endereco.createResponseStub()));

        assertEquals(CP_0013 + " - []", exception.getMessage());

    }

    @Test
    void testeDeveDarErroCP_0014QuandoAoSalvarRequest() {

        CadastroUtilsSucesso cadastroUtilsSucesso = new CadastroUtilsSucesso();

        when(validaUsuarioExistente.validaUsuario(any())).thenReturn(true);
        when(cadastroRepository.save(any())).thenThrow(new BusinessException(CP_0014, "Erro ao salvar"));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastroUtilsSucesso.createResponseStub()));

        // Verifica se o código da exceção é o esperado
        assertEquals(CP_0014, exception.getCode());
    }


    @Test
    void testeDevedarErroCP_0019QuandoEmailEstiverNoFormatoInvalido() {

        CadastroUtilsErrorEmailInvalido cadastroUtilsError = new CadastroUtilsErrorEmailInvalido();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastroUtilsError.createResponseStub()));

        assertEquals(CP_0019, exception.getCode());

    }

    @Test
    void testDeveDarErroCP_0020QuandoEmailForDuplicado() {

        CadastroUtilsSucesso cadastroUtilsSucesso = new CadastroUtilsSucesso();

        when(cadastroRepository.existsByEmail(any())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastroUtilsSucesso.createResponseStub()));

        assertEquals(CP_0020, exception.getCode());
    }

    @Test
    void testDeveValidarSenhaForte() {

        CadastroUtilsSucesso cadastroUtilsSucesso = new CadastroUtilsSucesso();

        when(validaUsuarioExistente.validaUsuario(any())).thenReturn(true);
        when(validaSenhaCadastro.validaSecretForte(any())).thenReturn(true);
        when(cadastroRepository.save(any())).thenReturn(new LcCadastro());

        cadastroServiceImpl.cadastro(cadastroUtilsSucesso.createResponseStub());

        verify(validaUsuarioExistente, times(1)).validaUsuario(any());
        verify(validaSenhaCadastro, times(1)).validaSecretForte(any());
        verify(cadastroRepository, times(1)).save(any());
    }


    @Test
    void testDeveValidarSeUsuarioJaCadastrado() {
        CadastroUtilsSucesso cadastroUtilsSucesso = new CadastroUtilsSucesso();

        BusinessException businessException = assertThrows(BusinessException.class,
                () -> cadastroServiceImpl.cadastro(cadastroUtilsSucesso.createResponseStub()));

        assertEquals(CP_0027, businessException.getCode());
    }

    @Test
    void testDeveDarSucessoQuandoNotificaoforFalse() {

        CadastroUtilsSucessoComNotificacaoFalse cadastroUtilsSucesso = new CadastroUtilsSucessoComNotificacaoFalse();

        when(validaUsuarioExistente.validaUsuario(any())).thenReturn(true);
        when(validaSenhaCadastro.validaSecretForte(any())).thenReturn(true);
        when(cadastroRepository.save(any())).thenReturn(new LcCadastro());

        cadastroServiceImpl.cadastro(cadastroUtilsSucesso.createResponseStub());

        verify(validaUsuarioExistente, times(1)).validaUsuario(any());
        verify(validaSenhaCadastro, times(1)).validaSecretForte(any());
        verify(cadastroRepository, times(1)).save(any());
    }


}