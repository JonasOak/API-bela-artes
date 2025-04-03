package com.belaArtes.demo.controller.resources;

import com.belaArtes.demo.controller.services.PedidoService;
import com.belaArtes.demo.model.entities.Cliente;
import com.belaArtes.demo.model.entities.Pedido;
import com.belaArtes.demo.model.entities.Usuario;
import com.belaArtes.demo.model.entities.enums.Cargo;
import com.belaArtes.demo.model.entities.enums.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<List<Pedido>> findAll() {
        List<Pedido> list = service.buscarTodos();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable("id") int id) {
        Pedido pedido = service.buscarPorId(id);
        return ResponseEntity.ok().body(pedido);
    }
}