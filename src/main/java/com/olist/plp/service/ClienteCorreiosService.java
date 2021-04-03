package com.olist.plp.service;

import javax.xml.bind.JAXBElement;

import com.olist.correios.wsdl.BuscaCliente;
import com.olist.correios.wsdl.BuscaClienteResponse;
import com.olist.correios.wsdl.ClienteERP;

import org.springframework.stereotype.Service;

@Service
public class ClienteCorreiosService extends AbstractCorreiosService {

    public ClienteERP getClienteCorreios() {

        var buscaClienteRequest  = buildBuscaCliente();
        var buscaClienteResponse = this.<BuscaClienteResponse>callCorreiosWebServiceMethod(buscaClienteRequest);

        return buscaClienteResponse.getValue().getReturn();
    }


    private JAXBElement<BuscaCliente> buildBuscaCliente() {
     
        var buscaCliente = new BuscaCliente();
        
        buscaCliente.setUsuario(env.getProperty("correios.usuario"));
        buscaCliente.setSenha(env.getProperty("correios.senha"));
        buscaCliente.setIdCartaoPostagem(env.getProperty("correios.cartao"));
        buscaCliente.setIdContrato(env.getProperty("correios.contrato"));

        return correiosFactory.createBuscaCliente(buscaCliente);
    }
    
}
