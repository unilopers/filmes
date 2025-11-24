-- Inserir Gêneros
INSERT INTO generos (nome) VALUES ('Ação');
INSERT INTO generos (nome) VALUES ('Comédia');
INSERT INTO generos (nome) VALUES ('Drama');
INSERT INTO generos (nome) VALUES ('Terror');
INSERT INTO generos (nome) VALUES ('Ficção Científica');
INSERT INTO generos (nome) VALUES ('Romance');
INSERT INTO generos (nome) VALUES ('Aventura');

-- Inserir Salas
INSERT INTO salas (nome, capacidade) VALUES ('Sala 1 - IMAX', 150);
INSERT INTO salas (nome, capacidade) VALUES ('Sala 2 - 3D', 100);
INSERT INTO salas (nome, capacidade) VALUES ('Sala 3 - Standard', 80);
INSERT INTO salas (nome, capacidade) VALUES ('Sala 4 - VIP', 50);

-- Inserir Filmes
INSERT INTO filmes (titulo, duracao_min, ano) VALUES ('Vingadores: Ultimato', 181, 2019);
INSERT INTO filmes (titulo, duracao_min, ano) VALUES ('Interestelar', 169, 2014);
INSERT INTO filmes (titulo, duracao_min, ano) VALUES ('Parasita', 132, 2019);
INSERT INTO filmes (titulo, duracao_min, ano) VALUES ('Coringa', 122, 2019);
INSERT INTO filmes (titulo, duracao_min, ano) VALUES ('O Poderoso Chefão', 175, 1972);

-- Inserir Tipos de Ingresso
INSERT INTO tipos_ingresso (descricao, fator_preco, categoria_tecnica) VALUES ('Inteira 2D', 1.00, '2D');
INSERT INTO tipos_ingresso (descricao, fator_preco, categoria_tecnica) VALUES ('Meia 2D', 0.50, '2D');
INSERT INTO tipos_ingresso (descricao, fator_preco, categoria_tecnica) VALUES ('Inteira 3D', 1.50, '3D');
INSERT INTO tipos_ingresso (descricao, fator_preco, categoria_tecnica) VALUES ('Meia 3D', 0.75, '3D');
INSERT INTO tipos_ingresso (descricao, fator_preco, categoria_tecnica) VALUES ('VIP 2D', 1.80, '2D');

-- Inserir Homologacoes (validacao de salas)

-- Sala 1 (IMAX) homologada para 2D e 3D
INSERT INTO homologacoes (id_filme, id_sala, requisito_tecnico, status_validacao)
VALUES (1, 1, '2D', 'Aprovado');
INSERT INTO homologacoes (id_filme, id_sala, requisito_tecnico, status_validacao)
VALUES (1, 1, '3D', 'Aprovado');

-- Sala 2 (3D) homologada apenas para 3D
INSERT INTO homologacoes (id_filme, id_sala, requisito_tecnico, status_validacao)
VALUES (2, 2, '3D', 'Aprovado');

-- Sala 3 (Standard) homologada para 2D
INSERT INTO homologacoes (id_filme, id_sala, requisito_tecnico, status_validacao)
VALUES (3, 3, '2D', 'Aprovado');

-- Sala 4 (VIP) homologada para 2D
INSERT INTO homologacoes (id_filme, id_sala, requisito_tecnico, status_validacao)
VALUES (4, 4, '2D', 'Aprovado');

-- Relacionar Filmes com Gêneros
INSERT INTO genero_filme (id_filme, id_genero) VALUES (1, 1);
INSERT INTO genero_filme (id_filme, id_genero) VALUES (1, 7);
INSERT INTO genero_filme (id_filme, id_genero) VALUES (2, 5);
INSERT INTO genero_filme (id_filme, id_genero) VALUES (2, 3);
INSERT INTO genero_filme (id_filme, id_genero) VALUES (3, 3);
INSERT INTO genero_filme (id_filme, id_genero) VALUES (3, 4);
INSERT INTO genero_filme (id_filme, id_genero) VALUES (4, 3);
INSERT INTO genero_filme (id_filme, id_genero) VALUES (5, 3);
INSERT INTO genero_filme (id_filme, id_genero) VALUES (5, 1);

-- Inserir Usuários
INSERT INTO usuarios (nome, email) VALUES ('João Silva', 'joao.silva@example.com');
INSERT INTO usuarios (nome, email) VALUES ('Maria Santos', 'maria.santos@example.com');
INSERT INTO usuarios (nome, email) VALUES ('Pedro Oliveira', 'pedro.oliveira@example.com');
INSERT INTO usuarios (nome, email) VALUES ('Ana Costa', 'ana.costa@example.com');