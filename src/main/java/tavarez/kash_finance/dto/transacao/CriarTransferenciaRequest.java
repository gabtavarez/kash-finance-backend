package tavarez.kash_finance.dto.transacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tavarez.kash_finance.model.StatusTransacao;
import tavarez.kash_finance.model.TipoRecorrencia;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CriarTransferenciaRequest(
        @NotNull(message = "Usuario e obrigatorio")
        Long usuarioId,

        @NotBlank(message = "Descricao e obrigatoria")
        String descricao,

        @NotNull(message = "Valor e obrigatorio")
        @Positive(message = "Valor deve ser maior que zero")
        BigDecimal valor,

        @NotNull(message = "Status e obrigatorio")
        StatusTransacao status,

        @NotNull(message = "Data da transacao e obrigatoria")
        LocalDate dataTransacao,

        @NotNull(message = "Conta de origem e obrigatoria")
        Long contaOrigemId,

        @NotNull(message = "Conta de destino e obrigatoria")
        Long contaDestinoId,

        @NotNull(message = "Tipo de recorrencia e obrigatorio")
        TipoRecorrencia tipoRecorrencia,

        Integer totalParcelas
) {
}
