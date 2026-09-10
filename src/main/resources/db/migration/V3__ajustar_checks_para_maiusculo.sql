-- categorias.tipo
ALTER TABLE categorias DROP CONSTRAINT categorias_tipo_check;
ALTER TABLE categorias
    ALTER COLUMN tipo SET DEFAULT 'AMBOS';
ALTER TABLE categorias
    ADD CONSTRAINT categorias_tipo_check
        CHECK (tipo IN ('RECEITA', 'DESPESA', 'AMBOS'));

-- series_transacao.tipo_recorrencia
ALTER TABLE series_transacao DROP CONSTRAINT series_recorrencia_check;
ALTER TABLE series_transacao
    ADD CONSTRAINT series_recorrencia_check
        CHECK (tipo_recorrencia IN ('FIXA', 'PARCELADA'));

-- transacoes.tipo
ALTER TABLE transacoes DROP CONSTRAINT transacoes_tipo_check;
ALTER TABLE transacoes
    ADD CONSTRAINT transacoes_tipo_check
        CHECK (tipo IN ('RECEITA', 'DESPESA', 'TRANSFERENCIA_ENTRADA', 'TRANSFERENCIA_SAIDA'));

-- transacoes.status
ALTER TABLE transacoes DROP CONSTRAINT transacoes_status_check;
ALTER TABLE transacoes
    ALTER COLUMN status SET DEFAULT 'PENDENTE';
ALTER TABLE transacoes
    ADD CONSTRAINT transacoes_status_check
        CHECK (status IN ('PENDENTE', 'AGENDADO', 'CONFIRMADO', 'CONCILIADO'));