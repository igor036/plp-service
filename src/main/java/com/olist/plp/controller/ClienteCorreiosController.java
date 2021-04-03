package com.olist.plp.controller;

import com.olist.correios.wsdl.ClienteERP;
import com.olist.plp.service.ClienteCorreiosService;

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
}
