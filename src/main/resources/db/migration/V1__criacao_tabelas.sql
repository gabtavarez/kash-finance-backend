CREATE TABLE usuarios (
                          id SERIAL PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          senha VARCHAR(255) NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE contas (
                        id SERIAL PRIMARY KEY,
                        usuario_id INTEGER NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
                        nome VARCHAR(100) NOT NULL,
                        cor VARCHAR(50),
                        saldo_inicial NUMERIC(15, 2) NOT NULL DEFAULT 0,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categorias (
                            id SERIAL PRIMARY KEY,
                            usuario_id INTEGER REFERENCES usuarios(id) ON DELETE CASCADE,
                            nome VARCHAR(150) NOT NULL,
                            tipo VARCHAR(20) NOT NULL DEFAULT 'ambos',
                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                            CONSTRAINT categorias_tipo_check
                                CHECK (tipo IN ('receita', 'despesa', 'ambos'))
);

CREATE TABLE series_transacao (
                                  id SERIAL PRIMARY KEY,
                                  usuario_id INTEGER NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
                                  tipo_recorrencia VARCHAR(20) NOT NULL,
                                  total_parcelas INTEGER,
                                  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                                  CONSTRAINT series_recorrencia_check
                                      CHECK (tipo_recorrencia IN ('fixa', 'parcelada')),

                                  CONSTRAINT series_parcelas_check
                                      CHECK (total_parcelas IS NULL OR total_parcelas > 0)
);

CREATE TABLE transacoes (
                            id SERIAL PRIMARY KEY,
                            usuario_id INTEGER NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
                            serie_id INTEGER REFERENCES series_transacao(id) ON DELETE SET NULL,
                            conta_id INTEGER NOT NULL REFERENCES contas(id) ON DELETE RESTRICT,
                            categoria_id INTEGER REFERENCES categorias(id) ON DELETE SET NULL,

                            descricao VARCHAR(255) NOT NULL,
                            valor NUMERIC(15, 2) NOT NULL,
                            tipo VARCHAR(20) NOT NULL,
                            status VARCHAR(20) NOT NULL DEFAULT 'pendente',
                            data_transacao DATE NOT NULL,

                            parcela_atual INTEGER,
                            transacao_relacionada_id INTEGER REFERENCES transacoes(id) ON DELETE SET NULL,

                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                            CONSTRAINT transacoes_tipo_check
                                CHECK (tipo IN ('receita', 'despesa', 'transferencia_entrada', 'transferencia_saida')),

                            CONSTRAINT transacoes_status_check
                                CHECK (status IN ('pendente', 'agendado', 'confirmado', 'conciliado')),

                            CONSTRAINT transacoes_valor_check
                                CHECK (valor > 0)
);

CREATE INDEX idx_contas_usuario ON contas(usuario_id);
CREATE INDEX idx_categorias_usuario ON categorias(usuario_id);
CREATE INDEX idx_transacoes_usuario ON transacoes(usuario_id);
CREATE INDEX idx_transacoes_conta ON transacoes(conta_id);
CREATE INDEX idx_transacoes_data ON transacoes(data_transacao);
CREATE INDEX idx_transacoes_status ON transacoes(status);
CREATE INDEX idx_transacoes_relacionada ON transacoes(transacao_relacionada_id);