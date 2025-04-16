package com.belaArtes.demo.controller.resources;

import com.belaArtes.demo.controller.services.PedidoService;
import com.belaArtes.demo.model.dto.PedidoRequestDTO;
import com.belaArtes.demo.model.dto.PedidoResponseDTO;
import com.belaArtes.demo.model.entities.Pedido;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoResource(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> buscarTodos() {
        List<Pedido> pedidos = pedidoService.buscarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable int id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/detalhado/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoDetalhado(@PathVariable Integer id) {
        PedidoResponseDTO dto = pedidoService.buscarPedidoCompleto(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoRequestDTO dto) {
        Pedido pedidoSalvo = pedidoService.inserirPedidoDTO(dto);
        PedidoResponseDTO responseDTO = pedidoService.buscarPedidoCompleto(pedidoSalvo.getIdPedido());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pedidoSalvo.getIdPedido())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable int id, @RequestBody Pedido obj) {
        obj = pedidoService.atualizar(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}