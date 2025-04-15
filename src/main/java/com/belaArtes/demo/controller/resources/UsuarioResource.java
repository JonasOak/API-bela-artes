package com.belaArtes.demo.controller.resources;


import com.belaArtes.demo.controller.services.UsuarioService;
import com.belaArtes.demo.controller.services.exceptions.ResourceNotFoundException;
import com.belaArtes.demo.model.dto.UsuarioDTO;
import com.belaArtes.demo.model.entities.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/usuarios")

public class UsuarioResource {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioResource(UsuarioService usuarioService) {  // Deve ser o mesmo nome da classe
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            // Converte DTO para Entidade
            Usuario usuario = new Usuario();
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setSenhaHash(usuarioDTO.getSenhaHash());
            usuario.setCargo(usuarioDTO.getCargo());

            Usuario usuarioSalvo = usuarioService.inserir(usuario);
            return ResponseEntity.ok(usuarioSalvo);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> buscarTodos() {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable("id") int id)  {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable Integer id,
            @RequestBody Usuario usuarioAtualizado // Mude de UsuarioDTO para Usuario
    ) {
        try {
            Usuario usuarioSalvo = usuarioService.atualizar(id, usuarioAtualizado);
            return ResponseEntity.ok(usuarioSalvo);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer id) {
        usuarioService.deletar(id); // Já lança a exceção se não existir
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        try {
            Usuario usuario = usuarioService.buscarPorEmail(email);
            return ResponseEntity.ok(usuario);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
