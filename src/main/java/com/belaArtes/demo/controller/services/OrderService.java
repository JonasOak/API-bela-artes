package com.belaArtes.demo.controller.services;

import com.belaArtes.demo.controller.resources.exceptions.ClientException;
import com.belaArtes.demo.controller.services.exceptions.PedidoException;
import com.belaArtes.demo.model.entities.Cliente;
import com.belaArtes.demo.model.entities.Pedido;
import com.belaArtes.demo.model.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private PedidoRepository repository;
    @Autowired
    private ClienteService clienteService;

    private boolean checkRequirements(int idClient) throws ClientException {
        Cliente getDate = clienteService.getUserClientId(idClient);
        if (getDate == null) {
            throw new ClientException("Cliente não encontrado");
        }
        return true;
    }

    public List<Pedido> createOrderList(List<Pedido> orders) throws PedidoException {
        for (Pedido order : orders) {
            checkRequirements(order.getIdClient());
        }
        return repository.saveAll(orders);
    }
}
