package tavarez.kash_finance.dto.conta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CriarContaRequest(
        @NotNull(message = "Usuario e obrigatorio")
        Long usuarioId,

        @NotBlank(message = "Nome e obrigatorio")
        String nome,

        String cor,

        @NotNull(message = "Saldo inicial e obrigatorio")
        BigDecimal saldoInicial
) {
}
