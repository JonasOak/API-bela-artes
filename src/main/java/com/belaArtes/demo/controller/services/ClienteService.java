package com.belaArtes.demo.controller.services;

import com.belaArtes.demo.controller.services.exceptions.ResourceNotFoundException;
import com.belaArtes.demo.model.entities.Cliente;
import com.belaArtes.demo.model.repositories.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public List<Cliente> buscarTodos() {
        return repository.findAll();
    }

    public Cliente buscarPorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com ID " + id + " não encontrado"));
    }

    public Cliente inserir(Cliente obj) {
        return repository.save(obj);
    }

    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente com ID " + id + " não encontrado para deletar");
        }
        repository.deleteById(id);
    }

    public Cliente atualizar(int id, Cliente obj) {
        try {
            Cliente clienteExistente = repository.getReferenceById(id);
            atualizarDados(clienteExistente, obj);
            return repository.save(clienteExistente);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Cliente com ID " + id + " não encontrado para atualização");
        }
    }

    private void atualizarDados(Cliente clienteExistente, Cliente novoCliente) {
        if (novoCliente.getNome() != null) {
            clienteExistente.setNome(novoCliente.getNome());
        }
        if (novoCliente.getCpf() != null) {
            clienteExistente.setCpf(novoCliente.getCpf());
        }
        if (novoCliente.getTelefone() != null) {
            clienteExistente.setTelefone(novoCliente.getTelefone());
        }
        if (novoCliente.getLogradouro() != null) {
            clienteExistente.setLogradouro(novoCliente.getLogradouro());
        }
        if (novoCliente.getNumero() != null) {
            clienteExistente.setNumero(novoCliente.getNumero());
        }
        if (novoCliente.getBairro() != null) {
            clienteExistente.setBairro(novoCliente.getBairro());
        }
        if (novoCliente.getCep() != null) {
            clienteExistente.setCep(novoCliente.getCep());
        }
        if (novoCliente.getComplemento() != null) {
            clienteExistente.setComplemento(novoCliente.getComplemento());
        }
        if (novoCliente.getUsuario() != null) {
            clienteExistente.setUsuario(novoCliente.getUsuario());
        }
    }
}
