package com.belaArtes.demo.model.repositories;

import com.belaArtes.demo.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    Usuario findUsuarioByIdUsuario(int idUsuario);
}
