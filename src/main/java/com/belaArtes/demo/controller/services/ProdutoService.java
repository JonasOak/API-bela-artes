package com.belaArtes.demo.controller.services;

import com.belaArtes.demo.model.entities.Produto;
import com.belaArtes.demo.model.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarProduto() {
        return produtoRepository.findAll();
    }

    public Produto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
    }

    public Produto incluirProduto(Produto produto) {
        validarProduto(produto);
        return produtoRepository.save(produto);
    }

    public Produto editarProduto(Long id, Produto produtoAtualizado) {
        Produto produtoExistente = buscarProdutoPorId(id);

        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setDescricao(produtoAtualizado.getDescricao());
        produtoExistente.setCategoria(produtoAtualizado.getCategoria());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        produtoExistente.setUrlFoto(produtoAtualizado.getUrlFoto());
        produtoExistente.setEstoque(produtoAtualizado.getEstoque());

        return produtoRepository.save(produtoExistente);
    }

    public void excluirProduto(Long id) {
        Produto produto = buscarProdutoPorId(id);
        produtoRepository.delete(produto);
    }

    // Validar produto inseridos
    private void validarProduto(Produto produto) {
        if (produto.getPreco().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("O preço do produto não pode ser negativo!");
        }
        if (produto.getEstoque() < 0) {
            throw new RuntimeException("O estoque não pode ser negativo!");
        }
    }
}
