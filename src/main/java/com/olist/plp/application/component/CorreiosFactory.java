package com.olist.plp.application.component;

import javax.xml.bind.JAXBElement;

import com.olist.integration.correios.wsdl.BuscaCliente;
import com.olist.integration.correios.wsdl.BuscaServicos;
import com.olist.integration.correios.wsdl.GetStatusCartaoPostagem;
import com.olist.integration.correios.wsdl.ObjectFactory;
import com.olist.integration.correios.wsdl.SolicitaEtiquetas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CorreiosFactory {

    private static final String TIPO_DESTINATARIO_ETIQUETA = "C";
    
    private final ObjectFactory factory = new ObjectFactory();

    @Autowired
    private CorreiosPropperties properties;

    public JAXBElement<BuscaCliente> buildBuscaClienteRequest() {
     
        var buscaCliente = new BuscaCliente();
        
        buscaCliente.setUsuario(properties.getUsuario());
        buscaCliente.setSenha(properties.getSenha());
        buscaCliente.setIdCartaoPostagem(properties.getCartao());
        buscaCliente.setIdContrato(properties.getContrato());

        return factory.createBuscaCliente(buscaCliente);
    }

    public JAXBElement<GetStatusCartaoPostagem> buildGetStatusCartaoPostagemRequest() {

        var getStatusCartaoPostagem = new GetStatusCartaoPostagem();

        getStatusCartaoPostagem.setUsuario(properties.getUsuario());
        getStatusCartaoPostagem.setSenha(properties.getSenha());
        getStatusCartaoPostagem.setNumeroCartaoPostagem(properties.getCartao());

        return factory.createGetStatusCartaoPostagem(getStatusCartaoPostagem);
    }
    
    public JAXBElement<BuscaServicos> buildBuscaServicosRequest() {
        
        var buscaServicos = new BuscaServicos();

        buscaServicos.setUsuario(properties.getUsuario());
        buscaServicos.setSenha(properties.getSenha());
        buscaServicos.setIdCartaoPostagem(properties.getCartao());
        buscaServicos.setIdContrato(properties.getContrato());

        return factory.createBuscaServicos(buscaServicos);
    }

    public JAXBElement<SolicitaEtiquetas> buildSolicitarEtiquetas(String idServicoCorreios, int qtdEtiquetas) {

        var solicitarEtiqueta = new SolicitaEtiquetas();
        solicitarEtiqueta.setTipoDestinatario(TIPO_DESTINATARIO_ETIQUETA);
        solicitarEtiqueta.setIdServico(Long.valueOf(idServicoCorreios));        
        solicitarEtiqueta.setQtdEtiquetas(qtdEtiquetas);
        solicitarEtiqueta.setIdentificador(properties.getCnpj());
        solicitarEtiqueta.setUsuario(properties.getUsuario());
        solicitarEtiqueta.setSenha(properties.getSenha());

        return factory.createSolicitaEtiquetas(solicitarEtiqueta);
    }
}
