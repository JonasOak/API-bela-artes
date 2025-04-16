package com.belaArtes.demo.model.repositories;

import com.belaArtes.demo.model.entities.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
    Optional<ItemPedido> findById(Integer id);

}
