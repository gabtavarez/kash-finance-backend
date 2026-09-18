package tavarez.kash_finance.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tavarez.kash_finance.dto.categoria.AtualizarCategoriaRequest;
import tavarez.kash_finance.dto.categoria.CategoriaResponse;
import tavarez.kash_finance.dto.categoria.CriarCategoriaRequest;
import tavarez.kash_finance.model.Categoria;
import tavarez.kash_finance.model.Usuario;
import tavarez.kash_finance.repository.CategoriaRepository;
import tavarez.kash_finance.repository.UsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public CategoriaResponse criar(Long usuarioId, CriarCategoriaRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));

        // categoriaPai so existe se o request trouxer um categoriaPaiId (e' opcional)
        Categoria categoriaPai = null;
        if (request.categoriaPaiId() != null) {
            categoriaPai = categoriaRepository.findById(request.categoriaPaiId())
                    .orElseThrow(() -> new EntityNotFoundException("Categoria pai nao encontrada"));
        }

        Categoria categoria = new Categoria();
        categoria.setUsuario(usuario);
        categoria.setCategoriaPai(categoriaPai);
        categoria.setNome(request.nome());
        categoria.setTipo(request.tipo());

        Categoria categoriaSalva = categoriaRepository.save(categoria);

        return CategoriaResponse.from(categoriaSalva);
    }

    public List<CategoriaResponse> listarPorUsuario(Long usuarioId) {
        return categoriaRepository.findByUsuarioId(usuarioId).stream()
                .map(CategoriaResponse::from)
                .toList();
    }

    public List<CategoriaResponse> listarRaizes() {
        return categoriaRepository.findByCategoriaPaiIsNull().stream()
                .map(CategoriaResponse::from)
                .toList();
    }

    public CategoriaResponse buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria nao encontrada"));
        return CategoriaResponse.from(categoria);
    }

    public CategoriaResponse atualizar(Long id, AtualizarCategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria nao encontrada"));

        categoria.setNome(request.nome());
        categoria.setTipo(request.tipo());

        return CategoriaResponse.from(categoriaRepository.save(categoria));
    }

    public void deletar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new EntityNotFoundException("Categoria nao encontrada");
        }
        categoriaRepository.deleteById(id);
    }
}
