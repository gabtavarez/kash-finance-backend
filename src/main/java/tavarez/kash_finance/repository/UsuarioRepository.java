package tavarez.kash_finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tavarez.kash_finance.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
