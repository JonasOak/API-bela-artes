package com.belaArtes.demo.controller.resources;

import com.belaArtes.demo.controller.resources.exceptions.ClientException;
import com.belaArtes.demo.controller.services.OrderService;
import com.belaArtes.demo.controller.services.exceptions.PedidoException;
import com.belaArtes.demo.model.entities.Pedido;
import com.belaArtes.demo.model.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/pedidos")
public class PedidosResource {
    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity<?> requestOrder(@RequestBody List<Pedido> pedido) {
        try {
            if (service.createOrderList(pedido) != null) {
                return ResponseEntity.ok("Pedido cadastrado com sucesso");
            }
        } catch (ClientException | PedidoException e) {
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        }
        return ResponseEntity.badRequest().build();
    }


}
