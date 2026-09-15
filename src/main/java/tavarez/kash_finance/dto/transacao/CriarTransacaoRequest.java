package tavarez.kash_finance.dto.transacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tavarez.kash_finance.model.StatusTransacao;
import tavarez.kash_finance.model.TipoRecorrencia;
import tavarez.kash_finance.model.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CriarTransacaoRequest(
        @NotNull(message = "Usuario e obrigatorio")
        Long usuarioId,

        @NotBlank(message = "Descricao e obrigatoria")
        String descricao,

        @NotNull(message = "Valor e obrigatorio")
        @Positive(message = "Valor deve ser maior que zero")
        BigDecimal valor,

        @NotNull(message = "Tipo e obrigatorio")
        TipoTransacao tipo,

        @NotNull(message = "Status e obrigatorio")
        StatusTransacao status,

        @NotNull(message = "Data da transacao e obrigatoria")
        LocalDate dataTransacao,

        Long categoriaId,

        @NotNull(message = "Conta e obrigatoria")
        Long contaId,

        @NotNull(message = "Tipo de recorrencia e obrigatorio")
        TipoRecorrencia tipoRecorrencia,

        Integer totalParcelas
) {
}
