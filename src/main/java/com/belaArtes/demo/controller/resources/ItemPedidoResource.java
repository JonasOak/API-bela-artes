package com.belaArtes.demo.controller.resources;


import com.belaArtes.demo.controller.services.ItemPedidoService;
import com.belaArtes.demo.model.dto.ItemPedidoResponseDTO;
import com.belaArtes.demo.model.entities.ItemPedido;
import com.belaArtes.demo.model.entities.Produto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/itens-pedido")
public class ItemPedidoResource {

    private final ItemPedidoService itemPedidoService;

    @Autowired
    public ItemPedidoResource(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @GetMapping
    public ResponseEntity<List<ItemPedidoResponseDTO>> buscarTodos() {
        List<ItemPedido> itens = itemPedidoService.buscarTodos();
        List<ItemPedidoResponseDTO> response = itens.stream()
                .map(this::converterEntidadeParaDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ItemPedidoResponseDTO> buscarPorId(@PathVariable int id) {
        ItemPedido item = itemPedidoService.buscarPorId(id);
        return ResponseEntity.ok(converterEntidadeParaDto(item));
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

        ItemPedido item = itemPedidoService.atualizar(id, itemAtualizado);
        return ResponseEntity.ok(item);
    }

    private ItemPedidoResponseDTO converterEntidadeParaDto(ItemPedido itemPedido) {
        Produto produto = itemPedido.getProduto();
        String imagemBase64 = Base64.getEncoder().encodeToString(produto.getImagem());

        return new ItemPedidoResponseDTO(
                itemPedido.getIdItemPedido(),
                itemPedido.getPedido().getIdPedido(),
                itemPedido.getPedido().getStatus().name(),
                itemPedido.getPedido().getDataPedido(),
                produto.getIdProduto(),
                produto.getNome(),
                imagemBase64,
                itemPedido.getQuantidade(),
                itemPedido.getPrecoUnitario()
        );
    }
}
