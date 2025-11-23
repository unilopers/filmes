-- Tabela de Gêneros
CREATE TABLE IF NOT EXISTS generos (
    id_genero BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
    );

-- Tabela de Salas
CREATE TABLE IF NOT EXISTS salas (
    id_sala BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50),
    capacidade INT
    );

-- Tabela de Filmes
CREATE TABLE IF NOT EXISTS filmes (
    id_filme BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    duracao_min INT,
    ano INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- Tabela de Tipos de Ingresso
CREATE TABLE IF NOT EXISTS tipos_ingresso (
    id_tipo_ingresso BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL UNIQUE,
    fator_preco DECIMAL(5,2),
    categoria_tecnica VARCHAR(20) DEFAULT '2D'
    );

-- Homologacoes
CREATE TABLE IF NOT EXISTS homologacoes (
    id_homologacao BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_filme BIGINT NOT NULL,
    id_sala BIGINT NOT NULL,
    requisito_tecnico VARCHAR(50),
    status_validacao VARCHAR(20),
    data_analise TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_filme) REFERENCES filmes(id_filme) ON DELETE CASCADE,
    FOREIGN KEY (id_sala) REFERENCES salas(id_sala) ON DELETE CASCADE
    );


-- Tabela de Gênero-Filme
CREATE TABLE IF NOT EXISTS genero_filme (
    id_filme BIGINT NOT NULL,
    id_genero BIGINT NOT NULL,
    PRIMARY KEY (id_filme, id_genero),
    FOREIGN KEY (id_filme) REFERENCES filmes(id_filme) ON DELETE CASCADE,
    FOREIGN KEY (id_genero) REFERENCES generos(id_genero) ON DELETE CASCADE
    );
