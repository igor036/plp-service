package com.olist.plp.adapter.correios;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBElement;

import com.olist.integration.correios.wsdl.BuscaClienteResponse;
import com.olist.integration.correios.wsdl.BuscaServicosResponse;
import com.olist.integration.correios.wsdl.ClienteERP;
import com.olist.integration.correios.wsdl.GetStatusCartaoPostagemResponse;
import com.olist.integration.correios.wsdl.ServicoERP;
import com.olist.integration.correios.wsdl.StatusCartao;
import com.olist.plp.application.component.CorreiosFactory;
import com.olist.integration.correios.wsdl.SolicitaEtiquetasResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
public class CorreiosWebService {

    @Autowired
    private CorreiosFactory correiosFactory;

    @Autowired
    protected WebServiceTemplate correiosWebServiceTemplate;

    public ClienteERP getClienteCorreios() {

        var buscaClienteRequest  = correiosFactory.buildBuscaClienteRequest();
        var buscaClienteResponse = this.<BuscaClienteResponse>
            callCorreiosWebServiceMethod(buscaClienteRequest);

        return buscaClienteResponse.getValue().getReturn();
    }

    public StatusCartao getStatusCartaoPostagem() {
        
        var getStatusCartaoPostagemRequest  = correiosFactory.buildGetStatusCartaoPostagemRequest();
        var getStatusCartaoPostagemResponse = this.<GetStatusCartaoPostagemResponse>
            callCorreiosWebServiceMethod(getStatusCartaoPostagemRequest);

        return getStatusCartaoPostagemResponse.getValue().getReturn();
    }


    public List<ServicoERP> getServicos() {

        var buscaServicosRequest  = correiosFactory.buildBuscaServicosRequest();
        var buscaServicosResponse = this.<BuscaServicosResponse> 
            callCorreiosWebServiceMethod(buscaServicosRequest);

        return buscaServicosResponse.getValue().getReturn();
    }


    public List<String> solicitarEtiquetas(String idServicoCorreios, int qtdEtiquetas) {

        var solicitarEtiquetasRequest = correiosFactory.
            buildSolicitarEtiquetas(idServicoCorreios, qtdEtiquetas);
        var solicitarEtiquetasResponse = this.<SolicitaEtiquetasResponse>
            callCorreiosWebServiceMethod(solicitarEtiquetasRequest);

        var etiquetas = solicitarEtiquetasResponse.getValue().getReturn();

        return Arrays.asList(etiquetas.split(","));
    }

    @SuppressWarnings("unchecked")
    private <T> JAXBElement<T> callCorreiosWebServiceMethod(Object request) {
        return (JAXBElement<T>) correiosWebServiceTemplate.marshalSendAndReceive(request);
    }
}
