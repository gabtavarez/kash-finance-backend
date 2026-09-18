package tavarez.kash_finance.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tavarez.kash_finance.dto.usuario.AtualizarUsuarioRequest;
import tavarez.kash_finance.dto.usuario.CriarUsuarioRequest;
import tavarez.kash_finance.dto.usuario.UsuarioResponse;
import tavarez.kash_finance.model.Usuario;
import tavarez.kash_finance.repository.UsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioResponse criar(CriarUsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(request.senha());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return UsuarioResponse.from(usuarioSalvo);
    }

    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponse::from)
                .toList();
    }

    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));
        return UsuarioResponse.from(usuario);
    }

    public UsuarioResponse atualizar(Long id, AtualizarUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));

        usuario.setNome(request.nome());
        usuario.setEmail(request.email());

        return UsuarioResponse.from(usuarioRepository.save(usuario));
    }

    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuario nao encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}