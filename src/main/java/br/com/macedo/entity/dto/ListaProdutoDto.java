package br.com.macedo.entity.dto;

import br.com.macedo.entity.ProdutoEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ListaProdutoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String descricaoProduto;

    private BigDecimal preco;

    public ListaProdutoDto(ProdutoEntity id) {
        this.descricaoProduto = id.getDescricaoProduto();
        this.preco = id.getPreco();
    }
}
