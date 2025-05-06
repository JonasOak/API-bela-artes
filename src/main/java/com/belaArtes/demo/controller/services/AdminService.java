package com.belaArtes.demo.controller.services;

import com.belaArtes.demo.model.repositories.ClienteRepository;
import com.belaArtes.demo.model.repositories.PedidoRepository;
import com.belaArtes.demo.model.repositories.ProdutoRepository;
import com.belaArtes.demo.model.repositories.UsuarioRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private ClienteRepository repositoryClient;
    @Autowired
    private ProdutoRepository repositoryProduct;
    @Autowired
    private PedidoRepository repositoryPedido;

    protected long countClient() {
        return repositoryClient.findAll().size();
    }

    protected long countProduct() {
        return repositoryProduct.findAll().size();
    }

    protected long countRequestOrders() {
        return repositoryPedido.findAll().size();
    }


    public AdminDto countService() {
        return new AdminDto(countClient(), countProduct(), countRequestOrders());
    }

    @Getter
    public class AdminDto {
        private long countClient;
        private long countProduct;
        private long countRequestOrders;

        public AdminDto(long countClient, long countProduct, long countRequestOrders) {
            this.countClient = countClient;
            this.countProduct = countProduct;
            this.countRequestOrders = countRequestOrders;
        }

    }
}

