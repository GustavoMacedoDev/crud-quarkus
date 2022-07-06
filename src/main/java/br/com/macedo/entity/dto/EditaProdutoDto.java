package br.com.macedo.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class EditaProdutoDto implements Serializable {
    private BigDecimal preco;
}
