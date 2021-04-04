package com.olist.plp.service;

import javax.xml.bind.JAXBElement;

import com.olist.correios.wsdl.BuscaCliente;
import com.olist.correios.wsdl.BuscaClienteResponse;
import com.olist.correios.wsdl.ClienteERP;
import com.olist.correios.wsdl.GetStatusCartaoPostagem;
import com.olist.correios.wsdl.GetStatusCartaoPostagemResponse;
import com.olist.correios.wsdl.StatusCartao;

import org.springframework.stereotype.Service;

@Service
public class ClienteCorreiosService extends AbstractCorreiosService {

    public ClienteERP getClienteCorreios() {

        var buscaClienteRequest  = buildBuscaClienteRequest();
        var buscaClienteResponse = this.<BuscaClienteResponse>
            callCorreiosWebServiceMethod(buscaClienteRequest);

        return buscaClienteResponse.getValue().getReturn();
    }

    public StatusCartao getStatusCartaoPostagem() {
        
        var getStatusCartaoPostagemRequest  = buildGetStatusCartaoPostagemRequest();
        var getStatusCartaoPostagemResponse = this.<GetStatusCartaoPostagemResponse>
            callCorreiosWebServiceMethod(getStatusCartaoPostagemRequest);

        return getStatusCartaoPostagemResponse.getValue().getReturn();
    }

    private JAXBElement<BuscaCliente> buildBuscaClienteRequest() {
     
        var buscaCliente = new BuscaCliente();
        
        buscaCliente.setUsuario(correiosPropperties.getUsuario());
        buscaCliente.setSenha(correiosPropperties.getSenha());
        buscaCliente.setIdCartaoPostagem(correiosPropperties.getCartao());
        buscaCliente.setIdContrato(correiosPropperties.getContrato());

        return correiosFactory.createBuscaCliente(buscaCliente);
    }

    private JAXBElement<GetStatusCartaoPostagem> buildGetStatusCartaoPostagemRequest() {

        var getStatusCartaoPostagem = new GetStatusCartaoPostagem();

        getStatusCartaoPostagem.setUsuario(correiosPropperties.getUsuario());
        getStatusCartaoPostagem.setSenha(correiosPropperties.getSenha());
        getStatusCartaoPostagem.setNumeroCartaoPostagem(correiosPropperties.getCartao());

        return correiosFactory.createGetStatusCartaoPostagem(getStatusCartaoPostagem);
    }
    
}
