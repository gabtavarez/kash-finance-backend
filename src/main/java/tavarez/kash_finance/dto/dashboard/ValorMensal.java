package tavarez.kash_finance.dto.dashboard;

import java.math.BigDecimal;

public record ValorMensal(
        BigDecimal confirmado,
        BigDecimal projetado
) {
}
