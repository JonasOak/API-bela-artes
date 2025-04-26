package com.belaArtes.demo.model.entities;

import com.belaArtes.demo.model.entities.enums.Cargo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
@Getter
@Setter
@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String senhaHash;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cargo cargo;

    public Usuario() {
    }

    public Usuario(int idUsuario, String email, String senhaHash, Cargo cargo) {
        this.idUsuario = idUsuario;
        this.email = email;
        this.senhaHash = senhaHash;
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", email='" + email + '\'' +
                ", senhaHash='" + senhaHash + '\'' +
                ", cargo=" + cargo +
                '}';
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return idUsuario == usuario.idUsuario;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idUsuario);
    }

    public static void main(String[] args) {
        Usuario u = new Usuario();
        u.setSenhaHash("123456");
        System.out.println(u);
    }
}
