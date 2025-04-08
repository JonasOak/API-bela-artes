package com.belaArtes.demo.controller.services;

import com.belaArtes.demo.model.entities.Produto;
import com.belaArtes.demo.model.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // Buscar todos os produtos
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    // Buscar produto por ID com validação
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
    }

    // Criar um novo produto
    public Produto criarProduto(Produto produto) {
        validarProduto(produto);
        return produtoRepository.save(produto);
    }

    // Atualizar um produto existente
    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        Produto produtoExistente = buscarPorId(id);

        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setDescricao(produtoAtualizado.getDescricao());
        produtoExistente.setCategoria(produtoAtualizado.getCategoria());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        produtoExistente.setUrlFoto(produtoAtualizado.getUrlFoto());
        produtoExistente.setEstoque(produtoAtualizado.getEstoque());

        return produtoRepository.save(produtoExistente);
    }

    // Deletar um produto
    public void deletarProduto(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }

    // Validação de produto
    private void validarProduto(Produto produto) {
        if (produto.getPreco().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("O preço do produto não pode ser negativo!");
        }
        if (produto.getEstoque() < 0) {
            throw new RuntimeException("O estoque não pode ser negativo!");
        }
    }
}