package com.belaArtes.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
@Getter
@Setter
public class ProdutoDTO {
    private int idProduto;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String descricao;

    @NotBlank(message = "Categoria é obrigatória")
    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    private String categoria;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    private BigDecimal preco;

    //@NotNull(message = "Imagem é obrigatória")
    //private MultipartFile imagem;
    private String imagem;
    @NotNull(message = "Estoque é obrigatório")
    @Min(value = 0, message = "Estoque não pode ser negativo")
    private Integer estoque;

    public ProdutoDTO() {}

    @Override
    public String toString() {
        return "ProdutoDTO{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", categoria='" + categoria + '\'' +
                ", preco=" + preco +
                ", imagem='" + imagem + '\'' +
                ", estoque=" + estoque +
                '}';
    }
}
