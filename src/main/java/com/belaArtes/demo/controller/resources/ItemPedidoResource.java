package com.belaArtes.demo.controller.resources;


import com.belaArtes.demo.controller.services.ItemPedidoService;
import com.belaArtes.demo.model.entities.ItemPedido;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/itens-pedido")
public class ItemPedidoResource {

    private final ItemPedidoService itemPedidoService;

    @Autowired
    public ItemPedidoResource(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @GetMapping
    public ResponseEntity<List<ItemPedido>> buscarTodos() {
        List<ItemPedido> itens = itemPedidoService.buscarTodos();
        return ResponseEntity.ok(itens);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ItemPedido> buscarPorId(@PathVariable int id) {
        ItemPedido item = itemPedidoService.buscarPorId(id);
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<ItemPedido> criarItemPedido(@Valid @RequestBody ItemPedido item) {
        ItemPedido itemSalvo = itemPedidoService.inserir(item);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(itemSalvo.getIdItemPedido())
                .toUri();
        return ResponseEntity.created(uri).body(itemSalvo);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        itemPedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedido> atualizarItemPedido(
            @PathVariable int id,
            @RequestBody ItemPedido itemAtualizado) {

        // Versão simplificada (sem DTOs por enquanto)
        ItemPedido item = itemPedidoService.atualizar(id, itemAtualizado);
        return ResponseEntity.ok(item);
    }
}
