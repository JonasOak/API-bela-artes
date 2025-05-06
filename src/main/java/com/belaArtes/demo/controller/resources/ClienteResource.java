package com.belaArtes.demo.controller.resources;


import com.belaArtes.demo.controller.resources.exceptions.ClientException;
import com.belaArtes.demo.controller.services.ClienteService;
import com.belaArtes.demo.model.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<Cliente>> findAll() {
        List<Cliente> list = service.buscarTodos();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable("id") int id) {
        Cliente cliente = service.buscarPorId(id);
        return ResponseEntity.ok().body(cliente);
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody Cliente obj) {
        try {
            obj = service.inserir(obj);
            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(obj.getIdCliente()).toUri();
            System.out.println("Cliente inserido: " + obj);
            return ResponseEntity.ok().body("{\"message\": \"Cliente cadastrado\"}");
        } catch (ClientException e) {
            return ResponseEntity.ok().body("{\"message\": \"" + e.getMessage() + "\"}");
        }

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable int id, @RequestBody Cliente obj) {
        obj = service.atualizar(id, obj);
        return ResponseEntity.ok().body(obj);
    }


}
