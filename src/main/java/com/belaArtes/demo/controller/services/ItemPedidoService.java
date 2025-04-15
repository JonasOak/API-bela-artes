package com.belaArtes.demo.controller.services;

import com.belaArtes.demo.controller.services.exceptions.ResourceNotFoundException;
import com.belaArtes.demo.model.entities.ItemPedido;
import com.belaArtes.demo.model.repositories.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository repository;

    @Autowired
    public ItemPedidoService(ItemPedidoRepository repository) {
        this.repository = repository;
    }

    public List<ItemPedido> buscarTodos() {
        return repository.findAll();
    }

    public ItemPedido buscarPorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));
    }

    public ItemPedido inserir(ItemPedido item) {
        return repository.save(item);
    }

    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ItemPedido com ID " + id + " não encontrado para deletar");
        }
        repository.deleteById(id);
    }

    public ItemPedido atualizar(int id, ItemPedido obj) {
        try {
            ItemPedido itemExistente = repository.getReferenceById(id);
            atualizarDados(itemExistente, obj);
            return repository.save(itemExistente);
        } catch (Exception e) {
            throw new ResourceNotFoundException("ItemPedido com ID " + id + " não encontrado para atualização");
        }
    }

    private void atualizarDados(ItemPedido itemExistente, ItemPedido novoItem) {
        if (novoItem.getQuantidade() != null) {
            itemExistente.setQuantidade(novoItem.getQuantidade());
        }
        if (novoItem.getPrecoUnitario() != null) {
            itemExistente.setPrecoUnitario(novoItem.getPrecoUnitario());
        }
    }
}
