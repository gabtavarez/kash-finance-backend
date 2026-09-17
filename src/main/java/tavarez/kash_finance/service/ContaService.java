package tavarez.kash_finance.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tavarez.kash_finance.dto.conta.AtualizarContaRequest;
import tavarez.kash_finance.dto.conta.ContaResponse;
import tavarez.kash_finance.dto.conta.CriarContaRequest;
import tavarez.kash_finance.model.Conta;
import tavarez.kash_finance.model.Usuario;
import tavarez.kash_finance.repository.ContaRepository;
import tavarez.kash_finance.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final UsuarioRepository usuarioRepository;

    public ContaResponse criar(Long usuarioId, CriarContaRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));

        Conta conta = new Conta();
        conta.setUsuario(usuario);
        conta.setNome(request.nome());
        conta.setCor(request.cor());
        conta.setSaldoInicial(request.saldoInicial() != null ? request.saldoInicial() : BigDecimal.ZERO);

        Conta contaSalva = contaRepository.save(conta);

        return ContaResponse.from(contaSalva);
    }

    public List<ContaResponse> listarPorUsuario(Long usuarioId) {
        return contaRepository.findByUsuarioId(usuarioId).stream()
                .map(ContaResponse::from)
                .toList();
    }

    public ContaResponse buscarPorId(Long id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conta nao encontrada"));
        return ContaResponse.from(conta);
    }

    public ContaResponse atualizar(Long id, AtualizarContaRequest request) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conta nao encontrada"));

        conta.setNome(request.nome());
        conta.setCor(request.cor());
        conta.setSaldoInicial(request.saldoInicial());

        return ContaResponse.from(contaRepository.save(conta));
    }

    public void deletar(Long id) {
        if (!contaRepository.existsById(id)) {
            throw new EntityNotFoundException("Conta nao encontrada");
        }
        contaRepository.deleteById(id);
    }
}