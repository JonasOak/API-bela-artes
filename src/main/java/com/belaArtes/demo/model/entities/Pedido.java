package com.belaArtes.demo.model.entities;

import com.belaArtes.demo.model.entities.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
@Getter
@Setter
@Entity
@Table(name = "Pedido")
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private int idPedido;

    private int idClient;
    private int idProduct;

    @Column(nullable = false)
    private LocalDateTime dataPedido = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status = StatusPedido.PENDENTE;



//    @ManyToOne(optional = false)
//    @JoinColumn(name = "id_usuario", nullable = false)
//    private Usuario usuario;
//    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
//    private List<ItemPedido> itens;


    public Pedido() {
    }

    public Pedido(int idPedido, Usuario usuario, LocalDateTime dataPedido, StatusPedido status, List<ItemPedido> itens) {
        this.idPedido = idPedido;
       // this.usuario = usuario;
        this.dataPedido = dataPedido;
        this.status = status;
       // this.itens = itens;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", dataPedido=" + dataPedido +
                ", status=" + status +
                ", idClient=" + idClient +
                '}';
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return idPedido == pedido.idPedido;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idPedido);
    }
}
