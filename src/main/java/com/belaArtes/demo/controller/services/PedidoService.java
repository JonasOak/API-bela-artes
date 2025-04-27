package com.belaArtes.demo.controller.services;

import com.belaArtes.demo.controller.services.exceptions.ResourceNotFoundException;
import com.belaArtes.demo.model.dto.PedidoRequestDTO;
import com.belaArtes.demo.model.dto.PedidoResponseDTO;
import com.belaArtes.demo.model.entities.*;
import com.belaArtes.demo.model.entities.enums.StatusPedido;
import com.belaArtes.demo.model.repositories.ClienteRepository;
import com.belaArtes.demo.model.repositories.PedidoRepository;
import com.belaArtes.demo.model.repositories.ProdutoRepository;
import com.belaArtes.demo.model.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Pedido> buscarTodos() {
        return repository.findAll();
    }

    public Pedido buscarPorId(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
    }

    public PedidoResponseDTO buscarPedidoCompleto(Integer id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        return new PedidoResponseDTO(
                pedido.getIdPedido(),
                pedido.getDataPedido(),
                pedido.getStatus().toString(), null);

//        return new PedidoResponseDTO(
//                pedido.getIdPedido(),
//                pedido.getDataPedido(),
//                pedido.getStatus().toString(),
//                converterItensParaDTO(pedido.getItens())
//        );
    }

    private List<PedidoResponseDTO.ItemPedidoResumoDTO> converterItensParaDTO(List<ItemPedido> itens) {
        return itens.stream()
                .map(item -> new PedidoResponseDTO.ItemPedidoResumoDTO(
                        item.getIdItemPedido(),
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.getPrecoUnitario()
                ))
                .collect(Collectors.toList());
    }

//    public Pedido inserir(Pedido pedido) {
//        return repository.save(pedido);
//    }
public Pedido inserirPedidoDTO(PedidoRequestDTO dto) {
    Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

    Pedido pedido = new Pedido();
    //pedido.setUsuario(usuario);
    pedido.setStatus(StatusPedido.PENDENTE);

    List<ItemPedido> itens = dto.getItens().stream().map(itemDTO -> {
        Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(itemDTO.getQuantidade());
        item.setPrecoUnitario(produto.getPreco());
        return item;
    }).collect(Collectors.toList());
   // pedido.setItens(itens);
    return repository.save(pedido);
}

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Pedido atualizar(int id, Pedido obj) {
        try {
            Pedido pedido = repository.getReferenceById(id);
            atualizarDados(pedido, obj);
            return  repository.save(pedido);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void atualizarDados(Pedido pedido, Pedido obj) {
        if (obj.getStatus() != null && !obj.getStatus().equals(pedido.getStatus())) {
            pedido.setStatus(obj.getStatus());
        }
    }
}
