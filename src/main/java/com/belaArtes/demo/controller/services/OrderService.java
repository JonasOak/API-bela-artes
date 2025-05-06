package com.belaArtes.demo.controller.services;

import com.belaArtes.demo.controller.resources.exceptions.ClientException;
import com.belaArtes.demo.controller.services.exceptions.PedidoException;
import com.belaArtes.demo.model.entities.Cliente;
import com.belaArtes.demo.model.entities.Pedido;
import com.belaArtes.demo.model.entities.Produto;
import com.belaArtes.demo.model.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private PedidoRepository repository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ProdutoService produtoService;

    private boolean checkRequirements(int idClient) throws ClientException {
        Cliente getDate = clienteService.getUserClientId(idClient);
        if (getDate == null) {
            throw new ClientException("Cliente não encontrado");
        }
        return true;
    }

    public List<Produto> getListProductsRequest(){
        List<Pedido> orderRequest = repository.findAll();
        List<Produto> productsRequests = new ArrayList<>();
        for(Pedido product: orderRequest){
            Produto searchProduct = produtoService.findByProductId(product.getIdProduct());
            if(searchProduct != null){
                productsRequests.add(searchProduct);
            }

        }
        return productsRequests;
    }

    public List<Cliente> getListOrderRequest(){
        List<Pedido> orderRequest = repository.findAll();
        List<Cliente> ordersClient = new ArrayList<>();
        for(Pedido order: orderRequest){
            Cliente client = clienteService.findByClientId(order.getIdClient());
            ordersClient.add(client);
        }
        return ordersClient;
    }

    public List<Pedido> createOrderList(List<Pedido> orders) throws PedidoException {
        for (Pedido order : orders) {
            checkRequirements(order.getIdClient());
        }
        return repository.saveAll(orders);
    }
}
