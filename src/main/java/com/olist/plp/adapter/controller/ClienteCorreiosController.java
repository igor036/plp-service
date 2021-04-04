package com.olist.plp.adapter.controller;

import java.util.List;

import com.olist.integration.correios.wsdl.ClienteERP;
import com.olist.integration.correios.wsdl.ServicoERP;
import com.olist.integration.correios.wsdl.StatusCartao;
import com.olist.plp.domain.service.ClienteCorreiosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cliente")
public class ClienteCorreiosController {

    @Autowired
    private ClienteCorreiosService clienteCorreiosService;
    
    @GetMapping
    public ResponseEntity<ClienteERP> getCliente() {
        var cliente = clienteCorreiosService.getClienteCorreios();
        return ResponseEntity.status(HttpStatus.FOUND).body(cliente);
    }

    @GetMapping("cartao-postagem/status")
    public ResponseEntity<StatusCartao> getStatusCartaoPostagem() {
        var statusCartao = clienteCorreiosService.getStatusCartaoPostagem();
        return ResponseEntity.status(HttpStatus.FOUND).body(statusCartao);
    }

    @GetMapping("servicos")
    public ResponseEntity<List<ServicoERP>> getServicos() {
        var servicos = clienteCorreiosService.getServicos();
        return ResponseEntity.status(HttpStatus.FOUND).body(servicos);
    }
}

