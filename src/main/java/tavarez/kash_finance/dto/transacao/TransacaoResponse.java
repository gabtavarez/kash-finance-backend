package tavarez.kash_finance.dto.transacao;

import tavarez.kash_finance.model.StatusTransacao;
import tavarez.kash_finance.model.Transacao;
import tavarez.kash_finance.model.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoResponse(
        Long id,
        Long usuarioId,
        Long serieId,
        Long contaId,
        String contaNome,
        Long categoriaId,
        String categoriaNome,
        String descricao,
        BigDecimal valor,
        TipoTransacao tipo,
        StatusTransacao status,
        LocalDate dataTransacao,
        Integer parcelaAtual
) {

    public static TransacaoResponse from(Transacao transacao) {
        return new TransacaoResponse(
                transacao.getId(),
                transacao.getUsuario().getId(),
                transacao.getSerie() != null ? transacao.getSerie().getId() : null,
                transacao.getConta().getId(),
                transacao.getConta().getNome(),
                transacao.getCategoria() != null ? transacao.getCategoria().getId() : null,
                transacao.getCategoria() != null ? transacao.getCategoria().getNome() : null,
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getTipo(),
                transacao.getStatus(),
                transacao.getDataTransacao(),
                transacao.getParcelaAtual()
        );
    }
}