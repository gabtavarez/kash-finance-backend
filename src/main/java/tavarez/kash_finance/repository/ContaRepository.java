package tavarez.kash_finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavarez.kash_finance.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
}
