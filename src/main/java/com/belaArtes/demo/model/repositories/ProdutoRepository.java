package com.belaArtes.demo.model.repositories;

import com.belaArtes.demo.model.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Produto findByIdProduto(int idProduto);
}