
package tavarez.kash_finance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "series_transacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SerieTransacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_recorrencia", nullable = false)
    private TipoRecorrencia tipoRecorrencia;

    @Column(name = "total_parcelas")
    private Integer totalParcelas;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime  createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(ZoneOffset.UTC);
    }
}
