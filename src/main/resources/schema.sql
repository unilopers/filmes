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