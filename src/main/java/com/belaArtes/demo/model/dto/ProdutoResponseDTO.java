package com.belaArtes.demo.model.dto;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Base64;

public class ProdutoResponseDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private String categoria;
    private BigDecimal preco;
    private Integer estoque;
    private String imagemBase64;

    public ProdutoResponseDTO(Integer id, String nome, String descricao,
                              String categoria, BigDecimal preco,
                              Integer estoque, byte[] imagem) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.estoque = estoque;
        this.imagemBase64 = imagem != null ? Base64.getEncoder().encodeToString(imagem) : null;
    }


    // Getters (sem setters para imutabilidade)
    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getCategoria() { return categoria; }
    public BigDecimal getPreco() { return preco; }
    public String getImagemBase64() { return imagemBase64; }
    public Integer getEstoque() { return estoque; }
}
