package com.belaArtes.demo.controller.resources;


import com.belaArtes.demo.controller.services.ProdutoService;
import com.belaArtes.demo.model.dto.ProdutoDTO;
import com.belaArtes.demo.model.entities.Produto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.belaArtes.demo.model.dto.ProdutoDTO;
import com.belaArtes.demo.model.dto.ProdutoResponseDTO;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ObjectMapper objectMapper;

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoResource(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> buscarTodos() {
        List<Produto> produtos = produtoService.buscarTodos();
        List<ProdutoResponseDTO> response = produtos.stream()
                .map(this::converterEntidadeParaDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Integer id) {
        Produto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(converterEntidadeParaDto(produto));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProdutoResponseDTO> criarProduto(
            @Valid @RequestPart("dados") ProdutoDTO produtoDTO,
            @RequestPart("imagem") MultipartFile imagem) {

        ProdutoResponseDTO produtoSalvo = produtoService.salvar(produtoDTO, imagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(
            @PathVariable Integer id,
            @Valid @RequestPart("dados") ProdutoDTO produtoDTO,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) {
        Produto produto = converterDtoParaEntidade(produtoDTO, imagem);
        Produto produtoAtualizado = produtoService.atualizar(id, produto, imagem);
        return ResponseEntity.ok(converterEntidadeParaDto(produtoAtualizado));
    }


    @PutMapping("/update")
    public ResponseEntity<ProdutoResponseDTO> updateProduct(@RequestBody Produto produtoDTO) {
        if (produtoService.update(produtoDTO) != null) {

            return ResponseEntity.ok().body(converterEntidadeParaDto(produtoDTO));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Integer id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Métodos auxiliares:
    private Produto converterDtoParaEntidade(ProdutoDTO dto, MultipartFile imagem) {
        Produto produto = new Produto();

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setCategoria(dto.getCategoria());
        produto.setPreco(dto.getPreco());
        produto.setEstoque(dto.getEstoque());

        try {
            produto.setImagem(imagem.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar imagem", e);
        }

        return produto;
    }

    private ProdutoResponseDTO converterEntidadeParaDto(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getIdProduto(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getPreco(),
                produto.getEstoque(),
                produto.getImagem()
        );
    }
}
