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

-- Tabela de Sessões
CREATE TABLE IF NOT EXISTS sessoes (
    id_sessao BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_filme BIGINT NOT NULL,
    id_sala BIGINT NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    preco_base DECIMAL(10,2),
    tipo_exibicao VARCHAR(20) DEFAULT '2D',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (id_filme) REFERENCES filmes(id_filme) ON DELETE CASCADE,
    FOREIGN KEY (id_sala) REFERENCES salas(id_sala) ON DELETE CASCADE
    );

-- Tabela de Assentos por Sessão
CREATE TABLE IF NOT EXISTS sessao_assento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_sessao BIGINT NOT NULL,
    id_assento INT NOT NULL,
    reservado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_sessao) REFERENCES sessoes(id_sessao) ON DELETE CASCADE,
    UNIQUE (id_sessao, id_assento)
    );

-- Tabela de Usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- Tabela de Ingressos
CREATE TABLE IF NOT EXISTS ingressos (
    id_ingresso BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_sessao BIGINT NOT NULL,
    id_tipo_ingresso BIGINT NOT NULL,
    id_assento INT NOT NULL,
    valor_final DECIMAL(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_sessao) REFERENCES sessoes(id_sessao) ON DELETE CASCADE,
    FOREIGN KEY (id_tipo_ingresso) REFERENCES tipos_ingresso(id_tipo_ingresso) ON DELETE CASCADE
    );