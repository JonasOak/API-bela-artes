package com.belaArtes.demo.controller.resources.exceptions;

import com.belaArtes.demo.model.entities.Produto;
import com.belaArtes.demo.model.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

   @Autowired
    private ProdutoRepository produtoRepository;

    // ✅ Incluir (Salvar)
    @PostMapping
    public Produto incluirProduto(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    // 📄 Listar todos
    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    // 📝 Editar produto por ID
    @PutMapping("/{id}")
    public ResponseEntity<Produto> editarProduto(@PathVariable Long id, @RequestBody Produto dadosAtualizados) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);

        if (optionalProduto.isPresent()) {
            Produto produto = optionalProduto.get();
            produto.setNome(dadosAtualizados.getNome());
            produto.setPreco(dadosAtualizados.getPreco());
            return ResponseEntity.ok(produtoRepository.save(produto));
        }

        return ResponseEntity.notFound().build();
    }

    // 🗑️ Excluir por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    // 🔍 Buscar produto por ID (opcional)
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}