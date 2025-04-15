package com.belaArtes.demo.controller.services;


import com.belaArtes.demo.controller.services.exceptions.ResourceNotFoundException;
import com.belaArtes.demo.model.entities.Produto;
import com.belaArtes.demo.model.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.repository = produtoRepository;
    }
    public List<Produto> buscarTodos() {
        return repository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }

    public Produto inserir(Produto produto) {
        validarProduto(produto);
        return repository.save(produto);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Produto com ID " + id + " não encontrado para deletar");
        }
        repository.deleteById(id);
    }

    public Produto atualizar(Long id, Produto produto) {
        try {
            Produto produtoExistente = repository.getReferenceById(id);
            atualizarDados(produtoExistente, produto);
            return repository.save(produtoExistente);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Produto com ID " + id + " não encontrado para atualização");
        }
    }

    private void validarProduto(Produto produto) {
        if (produto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResourceNotFoundException("Preço deve ser maior que zero");
        }
        if (produto.getEstoque() < 0) {
            throw new ResourceNotFoundException("Estoque não pode ser negativo");
        }
    }

    private void atualizarDados(Produto produtoExistente, Produto novoProduto) {
        if (novoProduto.getNome() != null) {
            produtoExistente.setNome(novoProduto.getNome());
        }
        if (novoProduto.getDescricao() != null) {
            produtoExistente.setDescricao(novoProduto.getDescricao());
        }
        if (novoProduto.getCategoria() != null) {
            produtoExistente.setCategoria(novoProduto.getCategoria());
        }
        if (novoProduto.getPreco() != null) {
            produtoExistente.setPreco(novoProduto.getPreco());
        }
        if (novoProduto.getUrlFoto() != null) {
            produtoExistente.setUrlFoto(novoProduto.getUrlFoto());
        }
        if (novoProduto.getEstoque() != null) {
            produtoExistente.setEstoque(novoProduto.getEstoque());
        }
    }
}
