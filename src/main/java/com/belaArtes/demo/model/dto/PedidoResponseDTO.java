package com.belaArtes.demo.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoResponseDTO {

    private Integer id;
    private LocalDateTime dataPedido;
    private String status;
    private List<ItemPedidoResumoDTO> itens;
    private BigDecimal valorTotal;

    public PedidoResponseDTO(Integer id, LocalDateTime dataPedido, String status,
                             List<ItemPedidoResumoDTO> itens) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.status = status;
        this.itens = itens;
        this.valorTotal = calcularValorTotal(itens);
    }

    private BigDecimal calcularValorTotal(List<ItemPedidoResumoDTO> itens) {
        return itens.stream()
                .map(ItemPedidoResumoDTO::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Integer getId() { return id; }
    public LocalDateTime getDataPedido() { return dataPedido; }
    public String getStatus() { return status; }
    public List<ItemPedidoResumoDTO> getItens() { return itens; }
    public BigDecimal getValorTotal() { return valorTotal; }

    public static class ItemPedidoResumoDTO {
        private Integer id;
        private String produtoNome;
        private Integer quantidade;
        private BigDecimal precoUnitario;
        private BigDecimal valorTotal;

        public ItemPedidoResumoDTO(Integer id, String produtoNome,
                                   Integer quantidade, BigDecimal precoUnitario) {
            this.id = id;
            this.produtoNome = produtoNome;
            this.quantidade = quantidade;
            this.precoUnitario = precoUnitario;
            this.valorTotal = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
        }

        public Integer getId() { return id; }
        public String getProdutoNome() { return produtoNome; }
        public Integer getQuantidade() { return quantidade; }
        public BigDecimal getPrecoUnitario() { return precoUnitario; }
        public BigDecimal getValorTotal() { return valorTotal; }
    }
}
