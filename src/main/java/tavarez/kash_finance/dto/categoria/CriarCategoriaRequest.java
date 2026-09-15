package tavarez.kash_finance.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tavarez.kash_finance.model.TipoCategoria;

public record CriarCategoriaRequest(
        @NotNull(message = "Usuario e obrigatorio")
        Long usuarioId,

        @NotBlank(message = "Nome e obrigatorio")
        String nome,

        @NotNull(message = "Tipo e obrigatorio")
        TipoCategoria tipo,

        Long categoriaPaiId
) {
}
