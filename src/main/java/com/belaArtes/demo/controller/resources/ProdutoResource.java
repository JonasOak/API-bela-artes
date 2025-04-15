package com.belaArtes.demo.controller.resources;


import com.belaArtes.demo.controller.services.ProdutoService;
import com.belaArtes.demo.model.dto.ProdutoDTO;
import com.belaArtes.demo.model.entities.Produto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.belaArtes.demo.model.dto.ProdutoDTO;
import com.belaArtes.demo.model.dto.ProdutoResponseDTO;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoResource(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> buscarTodos() {
        List<Produto> produtos = produtoService.buscarTodos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable int id) {
        Produto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<Produto> criarProduto(@Valid @RequestBody Produto produto) {
        Produto produtoSalvo = produtoService.inserir(produto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produtoSalvo.getIdProduto())
                .toUri();
        return ResponseEntity.created(uri).body(produtoSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable int id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(
            @PathVariable int id,
            @Valid @RequestBody ProdutoDTO produtoDTO) {

        // 1. Converte DTO para Entidade
        Produto produtoAtualizado = converterDtoParaEntidade(produtoDTO);

        // 2. Chama o service (que ainda trabalha com entidade)
        Produto produto = produtoService.atualizar(id, produtoAtualizado);

        // 3. Converte a entidade para DTO de resposta
        ProdutoResponseDTO responseDto = converterEntidadeParaDto(produto);

        return ResponseEntity.ok(responseDto);
    }

    // Métodos auxiliares:
    private Produto converterDtoParaEntidade(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setCategoria(dto.getCategoria());
        produto.setPreco(dto.getPreco());
        produto.setUrlFoto(dto.getUrlFoto());
        produto.setEstoque(dto.getEstoque());
        return produto;
    }

    private ProdutoResponseDTO converterEntidadeParaDto(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getIdProduto(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getPreco(),
                produto.getUrlFoto(),
                produto.getEstoque()
        );
    }
}
