package tavarez.kash_finance.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tavarez.kash_finance.dto.transacao.AtualizarTransacaoRequest;
import tavarez.kash_finance.dto.transacao.CriarTransacaoRequest;
import tavarez.kash_finance.dto.transacao.CriarTransferenciaRequest;
import tavarez.kash_finance.dto.transacao.TransacaoResponse;
import tavarez.kash_finance.model.*;
import tavarez.kash_finance.repository.CategoriaRepository;
import tavarez.kash_finance.repository.ContaRepository;
import tavarez.kash_finance.repository.SerieTransacaoRepository;
import tavarez.kash_finance.repository.TransacaoRepository;
import tavarez.kash_finance.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private static final int MESES_GERACAO_FIXA = 12;

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;
    private final CategoriaRepository categoriaRepository;
    private final SerieTransacaoRepository serieTransacaoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public List<TransacaoResponse> criar(Long usuarioId, CriarTransacaoRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));

        Conta conta = contaRepository.findById(request.contaId())
                .orElseThrow(() -> new EntityNotFoundException("Conta nao encontrada"));

        Categoria categoria = null;
        if (request.categoriaId() != null) {
            categoria = categoriaRepository.findById(request.categoriaId())
                    .orElseThrow(() -> new EntityNotFoundException("Categoria nao encontrada"));
        }


        if (request.tipoRecorrencia() == null || request.tipoRecorrencia() == TipoRecorrencia.UNICA) {
            Transacao transacao = construirTransacao(usuario, conta, categoria, request, null, null);
            Transacao salva = transacaoRepository.save(transacao);
            return List.of(TransacaoResponse.from(salva));
        }


        SerieTransacao serie = new SerieTransacao();
        serie.setUsuario(usuario);
        serie.setTipoRecorrencia(request.tipoRecorrencia());

        boolean parcelada = request.tipoRecorrencia() == TipoRecorrencia.PARCELADA;
        int totalParcelas = parcelada ? request.totalParcelas() : MESES_GERACAO_FIXA;
        serie.setTotalParcelas(parcelada ? request.totalParcelas() : null);

        SerieTransacao serieSalva = serieTransacaoRepository.save(serie);

        List<Transacao> transacoesGeradas = new ArrayList<>();
        for (int i = 0; i < totalParcelas; i++) {
            Integer numeroParcela = parcelada ? i + 1 : null;
            Transacao transacao = construirTransacao(usuario, conta, categoria, request, serieSalva, numeroParcela);
            transacao.setDataTransacao(request.dataTransacao().plusMonths(i));
            transacoesGeradas.add(transacao);
        }

        List<Transacao> salvas = transacaoRepository.saveAll(transacoesGeradas);

        return salvas.stream().map(TransacaoResponse::from).toList();
    }

    private Transacao construirTransacao(Usuario usuario, Conta conta, Categoria categoria, CriarTransacaoRequest request, SerieTransacao serie, Integer parcelaAtual) {
        Transacao transacao = new Transacao();
        transacao.setUsuario(usuario);
        transacao.setConta(conta);
        transacao.setCategoria(categoria);
        transacao.setSerie(serie);
        transacao.setDescricao(request.descricao());
        transacao.setValor(request.valor());
        transacao.setTipo(request.tipoTransacao());
        transacao.setStatus(request.statusTransacao());
        transacao.setDataTransacao(request.dataTransacao());
        transacao.setParcelaAtual(parcelaAtual);
        return transacao;
    }


    @Transactional
    public List<TransacaoResponse> criarTransferencia(Long usuarioId, CriarTransferenciaRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));

        Conta contaOrigem = contaRepository.findById(request.contaOrigemId())
                .orElseThrow(() -> new EntityNotFoundException("Conta de origem nao encontrada"));

        Conta contaDestino = contaRepository.findById(request.contaDestinoId())
                .orElseThrow(() -> new EntityNotFoundException("Conta de destino nao encontrada"));

        Transacao saida = new Transacao();
        saida.setUsuario(usuario);
        saida.setConta(contaOrigem);
        saida.setDescricao(request.descricao());
        saida.setValor(request.valor());
        saida.setTipo(TipoTransacao.TRANSFERENCIA_SAIDA);
        saida.setStatus(request.statusTransacao());
        saida.setDataTransacao(request.dataTransacao());

        Transacao entrada = new Transacao();
        entrada.setUsuario(usuario);
        entrada.setConta(contaDestino);
        entrada.setDescricao(request.descricao());
        entrada.setValor(request.valor());
        entrada.setTipo(TipoTransacao.TRANSFERENCIA_ENTRADA);
        entrada.setStatus(request.statusTransacao());
        entrada.setDataTransacao(request.dataTransacao());


        Transacao saidaSalva = transacaoRepository.save(saida);
        entrada.setTransacaoRelacionada(saidaSalva);
        Transacao entradaSalva = transacaoRepository.save(entrada);


        saidaSalva.setTransacaoRelacionada(entradaSalva);
        transacaoRepository.save(saidaSalva);

        return List.of(TransacaoResponse.from(saidaSalva), TransacaoResponse.from(entradaSalva));
    }


    public List<TransacaoResponse> listarPorConta(Long contaId) {
        return transacaoRepository.findByContaId(contaId).stream()
                .map(TransacaoResponse::from)
                .toList();
    }

    public TransacaoResponse buscarPorId(Long id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transacao nao encontrada"));
        return TransacaoResponse.from(transacao);
    }


    public TransacaoResponse atualizar(Long id, AtualizarTransacaoRequest request) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transacao nao encontrada"));

        Categoria categoria = null;
        if (request.categoriaId() != null) {
            categoria = categoriaRepository.findById(request.categoriaId())
                    .orElseThrow(() -> new EntityNotFoundException("Categoria nao encontrada"));
        }

        transacao.setDescricao(request.descricao());
        transacao.setValor(request.valor());
        transacao.setTipo(request.tipoTransacao());
        transacao.setStatus(request.statusTransacao());
        transacao.setDataTransacao(request.dataTransacao());
        transacao.setCategoria(categoria);

        return TransacaoResponse.from(transacaoRepository.save(transacao));
    }

    @Transactional
    public void deletar(Long id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transacao nao encontrada"));


        if (transacao.getTransacaoRelacionada() != null) {
            transacaoRepository.delete(transacao.getTransacaoRelacionada());
        }

        transacaoRepository.delete(transacao);
    }
}