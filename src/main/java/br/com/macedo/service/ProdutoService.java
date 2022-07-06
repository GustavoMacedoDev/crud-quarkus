package br.com.macedo.service;

import br.com.macedo.entity.ProdutoEntity;
import br.com.macedo.entity.dto.CadastroProdutoDto;
import br.com.macedo.entity.dto.ListaProdutoDto;
import br.com.macedo.exceptions.NaoEncontradoException;
import br.com.macedo.util.MensagemResposta;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProdutoService {

    private static final Logger LOGGER = Logger.getLogger(ProdutoService.class);

    @Transactional
    public List<ListaProdutoDto> listaProdutos() {
        List<ListaProdutoDto> listaProdutoReponse = new ArrayList<>();

        List<ProdutoEntity> listaProdutosEntity = ProdutoEntity.listAll();
        listaProdutosEntity.sort(Comparator.comparing(ProdutoEntity::getIdProduto));

        for(ProdutoEntity produto : listaProdutosEntity) {
            ListaProdutoDto listaProdutoDto = new ListaProdutoDto(produto);
//            listaProdutoDto.setDescricaoProduto(produto.getDescricaoProduto());
//            listaProdutoDto.setPreco(produto.getPreco());

            listaProdutoReponse.add(listaProdutoDto);
        }

        return listaProdutoReponse;
    }

    @Transactional
    public MensagemResposta cadastrarProduto(CadastroProdutoDto cadastroProdutoDto) {
        ProdutoEntity produtoEntity = new ProdutoEntity();

        produtoEntity.setDescricaoProduto(cadastroProdutoDto.getDescricaoProduto());
        produtoEntity.setPreco(cadastroProdutoDto.getPreco());

        try {
            produtoEntity.persist();
        } catch (PersistenceException e) {
            LOGGER.error(e);
        }

        return new MensagemResposta(produtoEntity.getIdProduto(),
                "O produto do código " + produtoEntity.getIdProduto() +
                " foi incluído com sucesso!");
    }

    @Transactional
    public ListaProdutoDto buscaProdutoPorId(Long idProduto) {
        ProdutoEntity obj = pesquisaProduto(idProduto);

        return new ListaProdutoDto(obj);
    }

    private ProdutoEntity pesquisaProduto(Long idProduto) {
        Optional<ProdutoEntity> produto = ProdutoEntity.findByIdOptional(idProduto);

        return produto.orElseThrow(() -> new NaoEncontradoException(
                "Produto não encontrado"
        ));
    }
}
