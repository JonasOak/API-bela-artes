package com.belaArtes.demo.controller.resources;


import com.belaArtes.demo.controller.services.ItemPedidoService;
import com.belaArtes.demo.model.entities.ItemPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/itens-pedido")
public class ItemPedidoResource {

    @Autowired
    private ItemPedidoService service;

    @GetMapping
    public ResponseEntity<List<ItemPedido>> findAll() {
        List<ItemPedido> list = service.buscarTodos();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ItemPedido> buscarPorId(@PathVariable("id") int id) {
        ItemPedido item = service.buscarPorId(id);
        return ResponseEntity.ok().body(item);
    }

    @PostMapping
    public ResponseEntity<ItemPedido> inserir(@RequestBody ItemPedido obj) {
        obj = service.inserir(obj);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getIdItemPedido()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ItemPedido> atualizar(@PathVariable int id, @RequestBody ItemPedido obj) {
        obj = service.atualizar(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}
