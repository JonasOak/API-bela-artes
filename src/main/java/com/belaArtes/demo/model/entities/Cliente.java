package com.belaArtes.demo.model.entities;

import com.belaArtes.demo.model.util.JasyptCrypto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
@Getter
@Setter
@Entity
@Table(name = "Cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false, length = 255)
    private String nome;

    @Convert(converter = JasyptCrypto.class)
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Convert(converter = JasyptCrypto.class)
    @Column(length = 20)
    private String telefone;

    @Column(length = 255)
    private String logradouro;

    @Column(length = 10)
    private String numero;

    @Column(length = 100)
    private String bairro;

    @Column(length = 10)
    private String cep;

    @Column(length = 255)
    private String complemento;

    public Cliente() {
    }

    public Cliente(int idCliente, Usuario usuario, String nome, String cpf, String telefone, String logradouro, String numero, String bairro, String cep, String complemento) {
        this.idCliente = idCliente;
        this.usuario = usuario;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.complemento = complemento;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", usuario=" + usuario +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", numero='" + numero + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cep='" + cep + '\'' +
                ", complemento='" + complemento + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return idCliente == cliente.idCliente;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idCliente);
    }
}
