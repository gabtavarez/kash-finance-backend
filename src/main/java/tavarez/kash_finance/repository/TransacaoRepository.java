package tavarez.kash_finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavarez.kash_finance.model.Transacao;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByContaId(Long contaId);g
}
