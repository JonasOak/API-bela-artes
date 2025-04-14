package com.belaArtes.demo.controller.resources;

import com.belaArtes.demo.controller.services.ProdutoService;
import com.belaArtes.demo.model.entities.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    // Incluir (Salvar)
    @PostMapping
    public ResponseEntity<Produto> incluirProduto(@RequestBody Produto produto) {
        Produto novoProduto = produtoService.incluirProduto(produto);
        return ResponseEntity.ok(novoProduto);
    }

    // Listar todos
    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoService.listarProduto();
    }

    // Editar produto por ID
    @PutMapping("/{id}")
    public ResponseEntity<Produto> editarProduto(@PathVariable Long id, @RequestBody Produto dadosAtualizados) {
        try {
            Produto produtoAtualizado = produtoService.editarProduto(id, dadosAtualizados);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Excluir por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        try {
            produtoService.excluirProduto(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        try {
            Produto produto = produtoService.buscarProdutoPorId(id);
            return ResponseEntity.ok(produto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}