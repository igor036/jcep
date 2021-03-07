package com.linecode.jcep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBElement;

import com.linecode.jcep.wsdl.ConsultaCEP;
import com.linecode.jcep.wsdl.ConsultaCEPResponse;
import com.linecode.jcep.wsdl.ObjectFactory;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.StringUtils;
import org.springframework.ws.client.core.WebServiceTemplate;

public class CorreiosService {
    
    private static final String EMPTY_CEP_VALUE_ERRO_MESSAGE = "O cep informado como null ou empty string.";

    private static final Pattern VALIDATION_CEP_PATTERN = Pattern.compile("^[0-9]{8}$"); 
    private static final String CORREIOS_SOURCE_CLASS_PACKAGE = "com.linecode.jcep.wsdl";
    private static final String CORREIOS_WEB_SERVICE_API_URI = "https://apps.correios.com.br/SigepMasterJPA/AtendeClienteService/AtendeCliente";
    
    private final WebServiceTemplate correiosWebServiceTemplate;

    public CorreiosService() {
        correiosWebServiceTemplate = buildCorreiosWebServiceTemplate();
    }
    
    public CompletableFuture<ConsultaCEPResponse> consultarCepAsync(String cep) {
        
        var thread = Executors.newSingleThreadExecutor();

        //@formatter:off
        return CompletableFuture
            .supplyAsync(() -> consultarCep(cep), thread)
            .whenComplete((response, exception) -> thread.shutdown());
        //@formatter:on
    }

    public ConsultaCEPResponse consultarCep(String cep) {

        assertCep(cep);

        var consultaCEP = new ConsultaCEP();
        consultaCEP.setCep(cep);

        var request  = new ObjectFactory().createConsultaCEP(consultaCEP);
        var response = this.<ConsultaCEPResponse>callCorreiosWebServiceAction(request);
    
        return response.getValue();
    }

    private WebServiceTemplate buildCorreiosWebServiceTemplate () {

        var correiosWebServiceMarshaller = buildCorreiosWebServiceMarshaller();
        var webservice = new WebServiceTemplate(correiosWebServiceMarshaller);

        webservice.setDefaultUri(CORREIOS_WEB_SERVICE_API_URI);
        
        return webservice;
    }

    private Jaxb2Marshaller buildCorreiosWebServiceMarshaller() {

        var marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(CORREIOS_SOURCE_CLASS_PACKAGE);

        return marshaller;
    }

    private void assertCep(String cep) {
        if (!StringUtils.hasText(cep)) {
            throw new IllegalAccessError(EMPTY_CEP_VALUE_ERRO_MESSAGE);
        }

        if (!VALIDATION_CEP_PATTERN.matcher(cep).matches()) {
            throw new IllegalAccessError(EMPTY_CEP_VALUE_ERRO_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> JAXBElement<T> callCorreiosWebServiceAction(Object request) {
        return (JAXBElement<T>) correiosWebServiceTemplate.marshalSendAndReceive(request);
    }
}
