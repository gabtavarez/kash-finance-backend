package tavarez.kash_finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavarez.kash_finance.model.SerieTransacao;

public interface SerieTransacaoRepository extends JpaRepository<SerieTransacao, Long> {
}
