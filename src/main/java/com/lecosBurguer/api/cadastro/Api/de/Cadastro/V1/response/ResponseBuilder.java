package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.response;

import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.dto.RequestDTO;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.exceptions.BusinessException;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.repository.CadastroRepository;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.service.impl.CadastroServiceImpl;
import com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.utils.MensagemResolver;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.config.CadastroConfig.MESSAGE_SOURCE_BEAN_IDENTIFIER;
import static com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.enums.CadastroEnums.CP_0003;
import static com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.enums.CadastroEnums.CP_0018;

@Service
public class ResponseBuilder {

    private final CadastroServiceImpl cadastroService;

    private final ReloadableResourceBundleMessageSource messageSource;

    private final MensagemResolver resolver;
    private String message;
    private Response.MessageData messageData = new Response.MessageData();
    private Response.Item item = new Response.Item();

    public ResponseBuilder(CadastroServiceImpl cadastroService,
                               @Qualifier(MESSAGE_SOURCE_BEAN_IDENTIFIER) ReloadableResourceBundleMessageSource messageSource,
                           MensagemResolver resolver){
        this.cadastroService = cadastroService;
        this.messageSource = messageSource;
        this.resolver = resolver;
    }
    public Response createResponse(RequestDTO requestDTO) {
        Response response = new Response();
        Response.Data data = new Response.Data();
        List<Response.Item> items = new ArrayList<>();

        requestDTO.getItem().forEach(requestItem -> {

            item.setItemId(requestItem.getItemId());

            List<Response.ErrorDetail> errors = new ArrayList<>();
            try {
                cadastroService.cadastro(requestDTO);

                String mensagem = resolver.getMensagem(CP_0018.getCode());; // Obtém a mensagem pelo código
                String nomeCadastro = requestDTO.getItem().get(0).getCadastro().getNome().toString();

                messageData.setMessage(String.format("%s %s", mensagem, nomeCadastro));
                item.setData(messageData);

            } catch (BusinessException e) {

                message = messageSource.getMessage(e.getCode(), e.getArgs() , LocaleContextHolder.getLocale());

                Response.ErrorDetail error = new Response.ErrorDetail();
                error.setCode(e.getCode());
                error.setMessage(message); // Mensagem da exceção
                error.setAction("Corrija os dados informados e tente novamente.");
                errors.add(error);

                Response.Item er = new Response.Item();
                er.setError(errors);

                messageData.setMessage(e.getCode());
                item.setData(messageData);

            }

            // Adiciona os erros capturados ao item
            item.setError(errors);
            items.add(item);
        });

        data.setItems(items);
        response.setData(data);
        return response;
    }
}
