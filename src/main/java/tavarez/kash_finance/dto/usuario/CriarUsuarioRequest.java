package tavarez.kash_finance.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarUsuarioRequest(
        @NotBlank(message = "Nome e obrigatorio")
        String nome,

        @NotBlank(message = "Email e obrigatorio")
        @Email(message = "Email invalido")
        String email,

        @NotBlank(message = "Senha e obrigatoria")
        @Size(min = 8, message = "Senha deve ter pelo menos 8 caracteres")
        String senha
) {
}
