ALTER TABLE categorias
    ADD COLUMN categoria_pai_id INTEGER REFERENCES categorias(id) ON DELETE CASCADE;

CREATE INDEX idx_categorias_pai ON categorias(categoria_pai_id);