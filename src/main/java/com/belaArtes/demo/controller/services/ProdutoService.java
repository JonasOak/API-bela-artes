package com.belaArtes.demo.controller.services;


import com.belaArtes.demo.controller.services.exceptions.ResourceNotFoundException;
import com.belaArtes.demo.model.dto.ProdutoDTO;
import com.belaArtes.demo.model.dto.ProdutoResponseDTO;
import com.belaArtes.demo.model.entities.Produto;
import com.belaArtes.demo.model.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public Produto buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }

    public ProdutoResponseDTO salvar(ProdutoDTO dto, MultipartFile imagem) {
        Produto produto = new Produto();

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setCategoria(dto.getCategoria());
        produto.setPreco(dto.getPreco());
        produto.setEstoque(dto.getEstoque());

        if (imagem != null && !imagem.isEmpty()) {
            try {
                produto.setImagem(imagem.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Erro ao processar a imagem", e);
            }
        }

        Produto salvo = repository.save(produto);

        return new ProdutoResponseDTO(
                salvo.getIdProduto(),
                salvo.getNome(),
                salvo.getDescricao(),
                salvo.getCategoria(),
                salvo.getPreco(),
                salvo.getEstoque(),
                salvo.getImagem()
        );
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Produto com ID " + id + " não encontrado para deletar");
        }
        repository.deleteById(id);
    }

    public Produto atualizar(Integer id, Produto produto, MultipartFile imagem) {
        try {
            Produto produtoExistente = repository.getReferenceById(id);
            atualizarDados(produtoExistente, produto);

            if (imagem != null && !imagem.isEmpty()) {
                produtoExistente.setImagem(imagem.getBytes());
            }

            return repository.save(produtoExistente);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao processar imagem", e);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Produto com ID " + id + " não encontrado para atualização");
        }
    }

    // update product
    public Produto update(Produto produto) {
        Produto produtoExistente = repository.getReferenceById(produto.getIdProduto());
        if (produtoExistente != null) {
            return repository.save(produto);
        }
        return null;

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
        if (novoProduto.getEstoque() != null) {
            produtoExistente.setEstoque(novoProduto.getEstoque());
        }
    }
}
