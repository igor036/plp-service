package com.olist.plp.domain.service;

import java.util.List;

import javax.xml.bind.JAXBElement;

import com.olist.integration.correios.wsdl.BuscaCliente;
import com.olist.integration.correios.wsdl.BuscaClienteResponse;
import com.olist.integration.correios.wsdl.BuscaServicos;
import com.olist.integration.correios.wsdl.BuscaServicosResponse;
import com.olist.integration.correios.wsdl.ClienteERP;
import com.olist.integration.correios.wsdl.GetStatusCartaoPostagem;
import com.olist.integration.correios.wsdl.GetStatusCartaoPostagemResponse;
import com.olist.integration.correios.wsdl.ServicoERP;
import com.olist.integration.correios.wsdl.StatusCartao;

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

    public List<ServicoERP> getServicos() {

        var buscaServicosRequest  = buildBuscaServicosRequest();
        var buscaServicosResponse = this.<BuscaServicosResponse> 
            callCorreiosWebServiceMethod(buscaServicosRequest);

        return buscaServicosResponse.getValue().getReturn();
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
    
    private JAXBElement<BuscaServicos> buildBuscaServicosRequest() {
    
        var buscaServicos = new BuscaServicos();

        buscaServicos.setUsuario(correiosPropperties.getUsuario());
        buscaServicos.setSenha(correiosPropperties.getSenha());
        buscaServicos.setIdCartaoPostagem(correiosPropperties.getCartao());
        buscaServicos.setIdContrato(correiosPropperties.getContrato());

        return correiosFactory.createBuscaServicos(buscaServicos);
    }
}
