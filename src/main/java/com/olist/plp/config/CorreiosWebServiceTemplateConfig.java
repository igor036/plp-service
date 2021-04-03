package com.olist.plp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class CorreiosWebServiceTemplateConfig {
     
    @Value("${correios.target_package}")
    private String correiosTargetPackage;
    
    @Value("${correios.webservice_url}")
    private String correiosWebServiceUrl;

    @Bean("correiosWebServiceTemplate")
    public WebServiceTemplate correiosWebServiceTemplate(Jaxb2Marshaller marshaller) {
        var webservice = new WebServiceTemplate(marshaller);
        webservice.setDefaultUri(correiosWebServiceUrl);
        return webservice;
    }

    @Bean("correiosWebServiceMarshaller")
    public Jaxb2Marshaller correiosWebServiceMarshaller() {
        var marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(correiosTargetPackage);
        return marshaller;
    }
    
}
