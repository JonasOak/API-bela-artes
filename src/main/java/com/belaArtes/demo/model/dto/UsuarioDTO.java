package com.belaArtes.demo.model.dto;

import com.belaArtes.demo.model.entities.enums.Cargo;
import jakarta.validation.constraints.*;

public class UsuarioDTO {

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    private String senhaHash;

    private Cargo cargo;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
