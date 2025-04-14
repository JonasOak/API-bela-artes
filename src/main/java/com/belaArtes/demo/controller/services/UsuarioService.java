package com.belaArtes.demo.controller.services;


import com.belaArtes.demo.controller.services.exceptions.ResourceNotFoundException;
import com.belaArtes.demo.model.entities.Usuario;
import com.belaArtes.demo.model.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.repository = usuarioRepository;
    }

    public List<Usuario> buscarTodos() {
        return repository.findAll();
    }

    public Usuario buscarPorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado"));
    }

    public Usuario buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com email " + email + " não encontrado"));
    }

    public Usuario inserir(Usuario obj) {
        if (obj.getEmail() == null || obj.getEmail().isBlank()) {
            throw new ResourceNotFoundException("E-mail não pode ser vazio");
        }

        if (repository.existsByEmail(obj.getEmail())) {
            throw new ResourceNotFoundException("E-mail já cadastrado");
        }

        if (obj.getSenhaHash() == null || obj.getSenhaHash().isBlank()) {
            throw new ResourceNotFoundException("Senha não pode ser vazia");
        }

        if (obj.getCargo() == null) {
            throw new ResourceNotFoundException("Cargo não pode ser nulo");
        }

        return repository.save(obj);
    }

    public void deletar(int id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário com ID " + id + " não encontrado para deletar");
        }
        repository.deleteById(id);
    }

    public Usuario atualizar(int id, Usuario obj) {
        try {
            Usuario usuarioExistente = repository.getReferenceById(id);
            atualizarDados(usuarioExistente, obj);
            return repository.save(usuarioExistente);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Usuário com ID " + id + " não encontrado para atualização");
        }
    }

    private void atualizarDados(Usuario usuarioExistente, Usuario novoUsuario) {
        if (novoUsuario.getEmail() != null) {
            usuarioExistente.setEmail(novoUsuario.getEmail());
        }
        if (novoUsuario.getSenhaHash() != null) {
            usuarioExistente.setSenhaHash(novoUsuario.getSenhaHash());
        }
        if (novoUsuario.getCargo() != null) {
            usuarioExistente.setCargo(novoUsuario.getCargo());
        }
    }

    public boolean existePorEmail(String email) {
        return repository.existsByEmail(email);
    }
}
