package com.belaArtes.demo.model.util;

import com.belaArtes.demo.model.dto.ProdutoDTO;
import com.belaArtes.demo.model.entities.Produto;
import org.springframework.stereotype.Service;

import java.util.Base64;
@Service
public class DtoUtil {

    protected Produto converteParaEntidade(ProdutoDTO produto) {
        byte[] imgConvertido = Base64.getDecoder().decode(produto.getImagem());
        return new Produto(
                produto.getNome(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getPreco(),
                imgConvertido,
                produto.getEstoque()
        );
    }
}
