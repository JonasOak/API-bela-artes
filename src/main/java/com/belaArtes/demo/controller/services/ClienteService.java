package com.belaArtes.demo.controller.services;

import com.belaArtes.demo.controller.resources.exceptions.ClientException;
import com.belaArtes.demo.controller.services.exceptions.ResourceNotFoundException;
import com.belaArtes.demo.model.entities.Cliente;
import com.belaArtes.demo.model.repositories.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    /**
     * Injetando usuarioService
     */
    @Autowired
    private UsuarioService usuarioService;

    public List<Cliente> buscarTodos() {
        return repository.findAll();
    }

    public Cliente buscarPorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com ID " + id + " não encontrado"));
    }

    public Cliente inserir(Cliente obj) throws ClientException {
        if(checkDate(obj)){
            if (usuarioService.inserir(obj.getUsuario()) != null) {
                return repository.save(obj);
            }
        }
        throw new ClientException("Erro desconhecido");
    }

    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente com ID " + id + " não encontrado para deletar");
        }
        repository.deleteById(id);
    }

    public Cliente atualizar(int id, Cliente obj) {
        try {
            Cliente clienteExistente = repository.getReferenceById(id);
            atualizarDados(clienteExistente, obj);
            return repository.save(clienteExistente);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Cliente com ID " + id + " não encontrado para atualização");
        }
    }

    private void atualizarDados(Cliente clienteExistente, Cliente novoCliente) {
        if (novoCliente.getNome() != null) {
            clienteExistente.setNome(novoCliente.getNome());
        }
        if (novoCliente.getCpf() != null) {
            clienteExistente.setCpf(novoCliente.getCpf());
        }
        if (novoCliente.getTelefone() != null) {
            clienteExistente.setTelefone(novoCliente.getTelefone());
        }
        if (novoCliente.getLogradouro() != null) {
            clienteExistente.setLogradouro(novoCliente.getLogradouro());
        }
        if (novoCliente.getNumero() != null) {
            clienteExistente.setNumero(novoCliente.getNumero());
        }
        if (novoCliente.getBairro() != null) {
            clienteExistente.setBairro(novoCliente.getBairro());
        }
        if (novoCliente.getCep() != null) {
            clienteExistente.setCep(novoCliente.getCep());
        }
        if (novoCliente.getComplemento() != null) {
            clienteExistente.setComplemento(novoCliente.getComplemento());
        }
    }

//
//    /**
//     * salvar cliente
//     *
//     * @author Eduardo
//     * @since 1.0
//     */
//    public Cliente register(Cliente client) throws ClientException {
//        if (checkDate(client)) {
//            if (usuarioService.inserir(client.getUsuario()) != null) {
//                return repository.save(client);
//            }
//        }
//        return null;
//    }

    /**
     * @param cpf Recebe valor de um cpf
     * @return Retornar dados do cliente se existir
     */
    protected Cliente findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    /**
     * @param email Recebe valor de um email
     * @return Retornar dados do cliente se existir
     */
    protected Cliente findByEmail(String email) {
        return repository.findByUsuario_Email(email);
    }

    /**
     * @param id Recebe valor de um email
     * @return Retornar dados do cliente se existir
     */
    protected Cliente findByClientId(int id) {
        return repository.findByIdCliente(id);
    }

    /**
     * Verificar dados no banco de dados do cliente
     *
     * @author Eduardo
     * @since 1.0
     */
    protected boolean checkDate(Cliente checkCliente) throws ClientException {
        if (findByClientId(checkCliente.getIdCliente()) != null) {
            throw new ClientException("Este ID já está em uso. Tente novamente mais tarde.");
        }
        if (findByCpf(checkCliente.getCpf()) != null) {
            throw new ClientException("Este CPF já está cadastrado. Tente novamente com um CPF diferente.");
        }
        if (findByCpf(checkCliente.getCpf()) != null) {
            throw new ClientException("E-mail duplicado: o endereço informado já existe no sistema.");
        }
        return true;
    }
}
