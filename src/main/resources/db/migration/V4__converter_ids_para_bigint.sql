-- V4__converter_ids_para_bigint.sql

-- Remover as constraints de FK antes de alterar os tipos
ALTER TABLE contas DROP CONSTRAINT contas_usuario_id_fkey;
ALTER TABLE categorias DROP CONSTRAINT categorias_usuario_id_fkey;
ALTER TABLE categorias DROP CONSTRAINT categorias_categoria_pai_id_fkey;
ALTER TABLE series_transacao DROP CONSTRAINT series_transacao_usuario_id_fkey;
ALTER TABLE transacoes DROP CONSTRAINT transacoes_usuario_id_fkey;
ALTER TABLE transacoes DROP CONSTRAINT transacoes_serie_id_fkey;
ALTER TABLE transacoes DROP CONSTRAINT transacoes_conta_id_fkey;
ALTER TABLE transacoes DROP CONSTRAINT transacoes_categoria_id_fkey;
ALTER TABLE transacoes DROP CONSTRAINT transacoes_transacao_relacionada_id_fkey;

-- Converter as chaves primarias
ALTER TABLE usuarios ALTER COLUMN id TYPE BIGINT;
ALTER TABLE contas ALTER COLUMN id TYPE BIGINT;
ALTER TABLE categorias ALTER COLUMN id TYPE BIGINT;
ALTER TABLE series_transacao ALTER COLUMN id TYPE BIGINT;
ALTER TABLE transacoes ALTER COLUMN id TYPE BIGINT;

-- Converter as chaves estrangeiras
ALTER TABLE contas ALTER COLUMN usuario_id TYPE BIGINT;
ALTER TABLE categorias ALTER COLUMN usuario_id TYPE BIGINT;
ALTER TABLE categorias ALTER COLUMN categoria_pai_id TYPE BIGINT;
ALTER TABLE series_transacao ALTER COLUMN usuario_id TYPE BIGINT;
ALTER TABLE transacoes ALTER COLUMN usuario_id TYPE BIGINT;
ALTER TABLE transacoes ALTER COLUMN serie_id TYPE BIGINT;
ALTER TABLE transacoes ALTER COLUMN conta_id TYPE BIGINT;
ALTER TABLE transacoes ALTER COLUMN categoria_id TYPE BIGINT;
ALTER TABLE transacoes ALTER COLUMN transacao_relacionada_id TYPE BIGINT;

-- Recriar as constraints de FK
ALTER TABLE contas ADD CONSTRAINT contas_usuario_id_fkey
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE;

ALTER TABLE categorias ADD CONSTRAINT categorias_usuario_id_fkey
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE;

ALTER TABLE categorias ADD CONSTRAINT categorias_categoria_pai_id_fkey
    FOREIGN KEY (categoria_pai_id) REFERENCES categorias(id) ON DELETE CASCADE;

ALTER TABLE series_transacao ADD CONSTRAINT series_transacao_usuario_id_fkey
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE;

ALTER TABLE transacoes ADD CONSTRAINT transacoes_usuario_id_fkey
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE;

ALTER TABLE transacoes ADD CONSTRAINT transacoes_serie_id_fkey
    FOREIGN KEY (serie_id) REFERENCES series_transacao(id) ON DELETE SET NULL;

ALTER TABLE transacoes ADD CONSTRAINT transacoes_conta_id_fkey
    FOREIGN KEY (conta_id) REFERENCES contas(id) ON DELETE RESTRICT;

ALTER TABLE transacoes ADD CONSTRAINT transacoes_categoria_id_fkey
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE SET NULL;

ALTER TABLE transacoes ADD CONSTRAINT transacoes_transacao_relacionada_id_fkey
    FOREIGN KEY (transacao_relacionada_id) REFERENCES transacoes(id) ON DELETE SET NULL;