package tavarez.kash_finance.dto.categoria;

import tavarez.kash_finance.model.Categoria;
import tavarez.kash_finance.model.TipoCategoria;

public record CategoriaResponse(
        Long id,
        String nome,
        TipoCategoria tipo,
        Long categoriaPaiId,
        Long usuarioId
) {


    public static CategoriaResponse from(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNome(),
                categoria.getTipo(),
                categoria.getCategoriaPai() != null ? categoria.getCategoriaPai().getId() : null,
                categoria.getUsuario().getId()
        );
    }
}
