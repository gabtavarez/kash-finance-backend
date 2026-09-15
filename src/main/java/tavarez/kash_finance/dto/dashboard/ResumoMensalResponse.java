package tavarez.kash_finance.dto.dashboard;

public record ResumoMensalResponse(
        int ano,
        int mes,
        ValorMensal saldoAnterior,
        ValorMensal receitas,
        ValorMensal transferenciasEntrada,
        ValorMensal despesas,
        ValorMensal transferenciasSaida,
        ValorMensal resultado,
        ValorMensal saldoFinal
) {
}