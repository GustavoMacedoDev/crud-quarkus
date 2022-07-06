package br.com.macedo.resource;

import br.com.macedo.entity.dto.CadastroProdutoDto;
import br.com.macedo.entity.dto.ListaProdutoDto;
import br.com.macedo.service.ProdutoService;
import br.com.macedo.util.MensagemErro;
import br.com.macedo.util.MensagemResposta;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/produtos")
@Produces(APPLICATION_JSON)
@ApplicationScoped
@Tag(name = "Produtos")
public class ProdutoResource {

    @Inject
    ProdutoService produtoService;

    @GET
    @Path("")
    @Operation(summary = "Retorna uma lista de Produtos")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema()))
    @APIResponse(responseCode = "400", description = "Nenhum produto foi encontrado.")
    public Response listarProdutos() {

        List<ListaProdutoDto> listaProdutos = produtoService.listaProdutos();

        return Response.status(Response.Status.OK).entity(listaProdutos).build();
    }

    @POST
    @Operation(summary = "Cadastro de Produto", description = "Cadastro de Produto")
    @APIResponses({
    @APIResponse(responseCode = "201", description = "Cadastro de Produto com sucesso",
                content = @Content(mediaType = APPLICATION_JSON)),
    @APIResponse(responseCode = "400", description = "Cadastro não realizado")})
    public Response cadastroProduto(@NotNull(message = "Requisição não pode ser nula")
                                    @Valid CadastroProdutoDto cadastroProdutoDto) {

        MensagemResposta resposta = produtoService.cadastrarProduto(cadastroProdutoDto);

        return Response.status(Response.Status.CREATED).entity(resposta).build();
    }

    @GET
    @Path("/produto/{idProduto}")
    @Operation(summary = "Busca produto por Id", description = "Busca produto por Id")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON,
                schema = @Schema(implementation = ListaProdutoDto.class, type = SchemaType.DEFAULT)))
    @APIResponse(responseCode = "404", description = "Identificador não foi encontrado.",
            content = @Content(mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = MensagemErro.class, type = SchemaType.ARRAY)))
    @APIResponse(responseCode = "400", description = "Requisição invalida.",
            content = @Content(mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = MensagemErro.class, type = SchemaType.ARRAY)))
    @APIResponse(responseCode = "500", description = "Serviço indisponível, tente novamente mais tarde.",
            content = @Content(mediaType = APPLICATION_JSON,
                    schema = @Schema(implementation = MensagemErro.class, type = SchemaType.ARRAY)))
    public Response buscaProdutoPorId(@PathParam("idProduto") Long idProduto) {
        ListaProdutoDto listaProdutoDto = produtoService.buscaProdutoPorId(idProduto);

        return Response.status(Response.Status.OK).entity(listaProdutoDto).build();
    }
}
