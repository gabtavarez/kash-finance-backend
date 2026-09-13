package tavarez.kash_finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavarez.kash_finance.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
