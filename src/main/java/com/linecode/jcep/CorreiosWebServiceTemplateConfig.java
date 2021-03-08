package com.linecode.jcep;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class CorreiosWebServiceTemplateConfig {
    
    private static final String CORREIOS_SOURCE_CLASS_PACKAGE = "com.linecode.jcep.wsdl";
    private static final String CORREIOS_WEB_SERVICE_API_URI = "https://apps.correios.com.br/SigepMasterJPA/AtendeClienteService/AtendeCliente";

    @Bean("correiosWebServiceTemplate")
    public WebServiceTemplate correiosWebServiceTemplate(Jaxb2Marshaller marshaller) {
        var webservice = new WebServiceTemplate(marshaller);
        webservice.setDefaultUri(CORREIOS_WEB_SERVICE_API_URI);
        return webservice;
    }

    @Bean("correiosWebServiceMarshaller")
    public Jaxb2Marshaller correiosWebServiceMarshaller() {
        var marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(CORREIOS_SOURCE_CLASS_PACKAGE);
        return marshaller;
    }
}
