package tavarez.kash_finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavarez.kash_finance.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
