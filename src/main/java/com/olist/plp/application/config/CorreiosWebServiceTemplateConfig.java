package com.olist.plp.application.config;

import com.olist.plp.application.component.CorreiosPropperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class CorreiosWebServiceTemplateConfig {
    
    @Autowired
    private CorreiosPropperties correiosPropperties;
    
    @Bean("correiosWebServiceTemplate")
    public WebServiceTemplate correiosWebServiceTemplate(Jaxb2Marshaller marshaller) {
        var webservice = new WebServiceTemplate(marshaller);
        webservice.setDefaultUri(correiosPropperties.getWebserviceUrl());
        return webservice;
    }

    @Bean("correiosWebServiceMarshaller")
    public Jaxb2Marshaller correiosWebServiceMarshaller() {
        var marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(correiosPropperties.getTargetPackage());
        return marshaller;
    }
    
}
