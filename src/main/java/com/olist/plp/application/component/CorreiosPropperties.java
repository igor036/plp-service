package com.olist.plp.application.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class CorreiosPropperties {
    
    @Value("${correios.usuario}")
    private String usuario;

    @Value("${correios.senha}") 
    private String senha;

    @Value("${correios.cartao}")
    private String cartao;

    @Value("${correios.contrato}")
    private String contrato;

    @Value("${correios.cnpj}")
    private String cnpj;

    @Value("${correios.codigo_administrativo}")
    private String codigoAdministrativo;

    @Value("${correios.codigo_servico}")
    private String codigoServico;

    @Value("${correios.webservice_url}")
    private String webserviceUrl;

    @Value("${correios.target_package}")
    private String targetPackage;

}
