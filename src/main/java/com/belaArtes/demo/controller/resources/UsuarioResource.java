package com.belaArtes.demo.controller.resources;


import com.belaArtes.demo.controller.services.ClienteService;
import com.belaArtes.demo.controller.services.UsuarioService;
import com.belaArtes.demo.controller.services.exceptions.EmailJaCadastradoException;
import com.belaArtes.demo.controller.services.exceptions.ResourceNotFoundException;
import com.belaArtes.demo.model.dto.UsuarioDTO;
import com.belaArtes.demo.model.entities.Cliente;
import com.belaArtes.demo.model.entities.Usuario;
import com.belaArtes.demo.model.entities.enums.Cargo;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/usuarios")

public class UsuarioResource {
    @Autowired
    private ClienteService clienteService;

    private final UsuarioService usuarioService;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UsuarioResource(UsuarioService usuarioService, BCryptPasswordEncoder passwordEncoder) {  // Deve ser o mesmo nome da classe
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String senha = loginData.get("senha");

        try {
            Usuario usuario = usuarioService.buscarPorEmail(email);
            if (!passwordEncoder.matches(senha, usuario.getSenhaHash())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta");
            }
            if(!usuario.isActive()){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Conta desativada");
            }
            // Retorna os dados do cliente para serem armazenados no dispositivo Android
            return ResponseEntity.ok(clienteService.getUserClientId(usuario.getIdUsuario()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<?> criarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = new Usuario();
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setSenhaHash(usuarioDTO.getSenhaHash());
            if (usuarioDTO.getCargo() == null) {
                usuario.setCargo(Cargo.CLIENTE);
            } else {
                usuario.setCargo(usuarioDTO.getCargo());
            }
            Usuario usuarioSalvo = usuarioService.inserir(usuario);
            return ResponseEntity.ok(usuarioSalvo);
        } catch (EmailJaCadastradoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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
    public ResponseEntity<Usuario> buscarPorId(@PathVariable("id") int id) {
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
    public ResponseEntity<Map<String, String>> deletarUsuario(@PathVariable("id") Integer id) {
        clienteService.delete(id);
        usuarioService.deletar(id);
        return ResponseEntity.ok(Map.of("mensagem", "Conta excluída com sucesso"));
    }

    @PostMapping("/disable/{id}")
    public ResponseEntity<?> disableAccount(@PathVariable("id") Integer id) {
        usuarioService.disableAccount(id);
        return ResponseEntity.ok(Map.of("mensagem", "Conta desativada com sucesso"));
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
