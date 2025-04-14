package com.belaArtes.demo.model.dto;

import java.math.BigDecimal;

public class ProdutoResponseDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private String categoria;
    private BigDecimal preco;
    private String urlFoto;
    private Integer estoque;

    // Construtor
    public ProdutoResponseDTO(Integer id, String nome, String descricao,
                              String categoria, BigDecimal preco,
                              String urlFoto, Integer estoque) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.urlFoto = urlFoto;
        this.estoque = estoque;
    }

    // Getters (sem setters para imutabilidade)
    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getCategoria() { return categoria; }
    public BigDecimal getPreco() { return preco; }
    public String getUrlFoto() { return urlFoto; }
    public Integer getEstoque() { return estoque; }
}
