package tavarez.kash_finance.dto.usuario;

import tavarez.kash_finance.model.Usuario;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCreatedAt(),
                usuario.getUpdatedAt()
        );
    }
}
