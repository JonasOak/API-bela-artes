package com.belaArtes.demo.model.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PedidoRequestDTO {

    @NotNull(message = "ID do usuário é obrigatório")
    private Integer usuarioId;

    @NotNull(message = "A lista de itens é obrigatória")
    private List<ItemPedidoDTO> itens;

    public static class ItemPedidoDTO {
        @NotNull(message = "ID do produto é obrigatório")
        private Integer produtoId;

        @NotNull(message = "Quantidade é obrigatória")
        private Integer quantidade;

        @NotNull(message = "Preço unitário é obrigatório")
        private Double precoUnitario;

        public Integer getProdutoId() {
            return produtoId;
        }

        public void setProdutoId(Integer produtoId) {
            this.produtoId = produtoId;
        }

        public Integer getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
        }

        public Double getPrecoUnitario() {
            return precoUnitario;
        }

        public void setPrecoUnitario(Double precoUnitario) {
            this.precoUnitario = precoUnitario;
        }
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoDTO> itens) {
        this.itens = itens;
    }
}
