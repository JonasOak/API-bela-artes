package com.belaArtes.demo.controller.services;

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
         return obj.get();
    }
}
