package com.belaArtes.demo.model.repositories;

import com.belaArtes.demo.model.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository  extends JpaRepository<Cliente, Integer> {


    Cliente findByCpf(String cpf);
    Cliente findByUsuario_Email(String email);

    Cliente findByIdCliente(int idCliente);
}
