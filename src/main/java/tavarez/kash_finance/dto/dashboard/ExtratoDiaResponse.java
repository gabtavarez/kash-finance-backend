package tavarez.kash_finance.dto.dashboard;

import tavarez.kash_finance.dto.transacao.TransacaoResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ExtratoDiaResponse(
        LocalDate data,
        List<TransacaoResponse> transacoes,
        BigDecimal saldoAcumulado
) {
}
