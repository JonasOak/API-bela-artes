package com.belaArtes.demo.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ItemPedidoResponseDTO {

    private Integer id;
    private PedidoResumoDTO pedido;
    private ProdutoResumoDTO produto;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal valorTotal;

    public ItemPedidoResponseDTO(Integer id, Integer pedidoId, String pedidoStatus,
                                 LocalDateTime pedidoData, Integer produtoId,
                                 String produtoNome, String produtoImagemBase64,
                                 Integer quantidade, BigDecimal precoUnitario) {
        this.id = id;
        this.pedido = new PedidoResumoDTO(pedidoId, pedidoStatus, pedidoData);
        this.produto = new ProdutoResumoDTO(produtoId, produtoNome, produtoImagemBase64);
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.valorTotal = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    public Integer getId() { return id; }
    public PedidoResumoDTO getPedido() { return pedido; }
    public ProdutoResumoDTO getProduto() { return produto; }
    public Integer getQuantidade() { return quantidade; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public BigDecimal getValorTotal() { return valorTotal; }

    public static class PedidoResumoDTO {
        private Integer id;
        private String status;
        private LocalDateTime data;

        public PedidoResumoDTO(Integer id, String status, LocalDateTime data) {
            this.id = id;
            this.status = status;
            this.data = data;
        }

        public Integer getId() { return id; }
        public String getStatus() { return status; }
        public LocalDateTime getData() { return data; }
    }

    public static class ProdutoResumoDTO {
        private Integer id;
        private String nome;
        private String imagemBase64;

        public ProdutoResumoDTO(Integer id, String nome, String imagemBase64) {
            this.id = id;
            this.nome = nome;
            this.imagemBase64 = imagemBase64;
        }

        public Integer getId() { return id; }
        public String getNome() { return nome; }
        public String getImagemBase64() { return imagemBase64; }
    }
}
