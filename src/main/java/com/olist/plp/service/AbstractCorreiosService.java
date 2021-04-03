package com.olist.plp.service;

import com.olist.correios.wsdl.ObjectFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.bind.JAXBElement;

public abstract class AbstractCorreiosService {

    protected ObjectFactory correiosFactory = new ObjectFactory();

    @Autowired
    protected WebServiceTemplate correiosWebServiceTemplate;

    @Autowired
    protected Environment env;

    @SuppressWarnings("unchecked")
    protected <T> JAXBElement<T> callCorreiosWebServiceMethod(Object request) {
        return (JAXBElement<T>) correiosWebServiceTemplate.marshalSendAndReceive(request);
    }

}
