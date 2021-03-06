package com.linecode.jcep;

import javax.xml.bind.JAXBElement;

import com.linecode.jcep.wsdl.ConsultaCEP;
import com.linecode.jcep.wsdl.ConsultaCEPResponse;
import com.linecode.jcep.wsdl.ObjectFactory;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

public class CorreiosService {
    
    private static final String CORREIOS_SOURCE_CLASS_PACKAGE = "com.linecode.jcep.wsdl";
    private static final String CORREIOS_WEB_SERVICE_API_URI = "https://apps.correios.com.br/SigepMasterJPA/AtendeClienteService/AtendeCliente";

    private WebServiceTemplate correiosWebServiceTemplate;

    public CorreiosService() {
        correiosWebServiceTemplate = buildCorreiosWebServiceTemplate();
    }

    public ConsultaCEPResponse consultarCep(String cep) {

        var consultaCEP = new ConsultaCEP();
        consultaCEP.setCep(cep);

        var request  = new ObjectFactory().createConsultaCEP(consultaCEP);
        var response = (JAXBElement<ConsultaCEPResponse>) correiosWebServiceTemplate.marshalSendAndReceive(request);
        
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
}
