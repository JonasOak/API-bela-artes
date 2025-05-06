package com.belaArtes.demo.controller.services;


import com.belaArtes.demo.controller.resources.exceptions.ClientException;
import com.belaArtes.demo.controller.services.exceptions.EmailJaCadastradoException;
import com.belaArtes.demo.controller.services.exceptions.ResourceNotFoundException;
import com.belaArtes.demo.model.entities.Usuario;
import com.belaArtes.demo.model.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
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
        if (repository.existsByEmail(obj.getEmail())) {
            throw new EmailJaCadastradoException(obj.getEmail());
        }
        String senhaCriptografada = passwordEncoder.encode(obj.getSenhaHash());
        obj.setSenhaHash(senhaCriptografada);
        return repository.save(obj);
    }

    public void deletar(int id) {
        Usuario searchUser = repository.findUsuarioByIdUsuario(id);
        if (searchUser == null) {
            throw new ResourceNotFoundException("Usuário com ID " + id + " não encontrado para deletar");
        }
        repository.deleteById(searchUser.getIdUsuario());
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
            String senhaCriptografada = passwordEncoder.encode(novoUsuario.getSenhaHash());
            usuarioExistente.setSenhaHash(senhaCriptografada);
        }
        if (novoUsuario.getCargo() != null) {
            usuarioExistente.setCargo(novoUsuario.getCargo());
        }
    }

    public void updateUser(Usuario user) throws ClientException {
        Usuario searchUser = repository.findUsuarioByIdUsuario(user.getIdUsuario());
        if(searchUser == null){
            throw new ClientException("Usuário não encontrado");
        }
        repository.save(user);
    }

    public void disableAccount(Integer id) {
        Usuario searchUser = repository.findUsuarioByIdUsuario(id);
        if(searchUser != null) {
            searchUser.setActive(false);
             repository.save(searchUser);
        }
    }

    public boolean existePorEmail(String email) {
        return repository.existsByEmail(email);
    }
}
