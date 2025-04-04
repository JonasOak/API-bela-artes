package com.belaArtes.demo.controller.services;

import com.belaArtes.demo.controller.services.exceptions.ResourceNotFoundException;
import com.belaArtes.demo.model.entities.Pedido;
import com.belaArtes.demo.model.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public List<Pedido> buscarTodos() {
        return repository.findAll();
    }

    public Pedido buscarPorId(int id) {
         Optional<Pedido> obj = repository.findById(id);
         return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Pedido inserir(Pedido obj) {
        return repository.save(obj);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Pedido atualizar(int id, Pedido obj) {
        Pedido pedido = repository.getReferenceById(id);
        atualizarDados(pedido, obj);
        return  repository.save(pedido);
    }

    private void atualizarDados(Pedido pedido, Pedido obj) {
        if (obj.getStatus() != null && !obj.getStatus().equals(pedido.getStatus())) {
            pedido.setStatus(obj.getStatus());
        }
    }
}
