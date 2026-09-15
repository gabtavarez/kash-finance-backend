package tavarez.kash_finance.dto.conta;

import tavarez.kash_finance.model.Conta;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ContaResponse(
        Long id,
        Long usuarioId,
        String nome,
        String cor,
        BigDecimal saldoInicial,
        LocalDateTime createdAt
) {

    public static ContaResponse from(Conta conta) {
        return new ContaResponse(
                conta.getId(),
                conta.getUsuario().getId(),
                conta.getNome(),
                conta.getCor(),
                conta.getSaldoInicial(),
                conta.getCreatedAt()
        );
    }
}
