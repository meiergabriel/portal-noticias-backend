Instruções

Baixar e instalar o maven

Baixar e instalar o mysql server community e mysql workbench 

criar um banco de dados chamado loja1. Criar as tabelas conforme abaixo, inserir os dados conforme abaixo.

ao clonar o projeto executar o comando abaixo para baixar as dependências do maven

mvn -N wrapper:wrapper

ao clonar o projeto, executar mvn wrapper:wrapper para baixar as dependencias e organizar o ambiente.

exercutar o comando abaixo para subir o projeto

./mvnw spring-boot:run

#Sql

CREATE TABLE `categoria` (
 id int(11) NOT NULL AUTO_INCREMENT,
 nome varchar(255) NOT NULL,
 descricao text DEFAULT NULL,
 categoria_pai_id int(11) DEFAULT NULL,
 PRIMARY KEY (id),
 KEY categoria_pai_id (categoria_pai_id),
 CONSTRAINT categoria_ibfk_1 FOREIGN KEY (categoria_pai_id) REFERENCES categoria (id) ON DELETE CASCADE
);



CREATE TABLE produto (
 id int(11) NOT NULL AUTO_INCREMENT,
 nome varchar(255) NOT NULL,
 descricao text DEFAULT NULL,
 valor decimal(10,2) NOT NULL,
 quantidade_estoque int(11) NOT NULL,
 categoria_id int(11) DEFAULT NULL,
 PRIMARY KEY (id),
 KEY categoria_id (categoria_id),
 CONSTRAINT produto_ibfk_1 FOREIGN KEY (categoria_id) REFERENCES categoria (id) ON DELETE CASCADE
);


-- Inserir 10 categorias (2 pais e 8 filhas)
INSERT INTO categoria (nome, descricao, categoria_pai_id)
VALUES 
    ('Eletrônicos', 'Produtos eletrônicos em geral', NULL), -- Categoria Pai 1
    ('Roupas', 'Roupas para todas as estações', NULL), -- Categoria Pai 2
    ('Celulares', 'Smartphones e acessórios', 1), -- Categoria Filha 1
    ('TVs', 'Televisões de alta definição', 1), -- Categoria Filha 2
    ('Camisetas', 'Camisetas casuais', 2), -- Categoria Filha 3
    ('Calças', 'Calças jeans e de tecido', 2), -- Categoria Filha 4
    ('Capas de celular', 'Capas protetoras para smartphones', 3), -- Categoria Filha 5
    ('Fones de ouvido', 'Fones de ouvido com fio e sem fio', 3), -- Categoria Filha 6
    ('Smart TVs', 'Televisões inteligentes', 4), -- Categoria Filha 7
    ('Roupas íntimas', 'Roupas íntimas para todas as idades', 5); -- Categoria Filha 8

-- Inserir 40 produtos associados apenas às categorias filhas
INSERT INTO produto (nome, descricao, valor, quantidade_estoque, categoria_id)
VALUES 
    ('iPhone 12', 'Smartphone da Apple', 4000, 50, 3),
    ('Samsung Galaxy S21', 'Smartphone da Samsung', 3500, 60, 3),
    ('Xiaomi Redmi Note 10', 'Smartphone da Xiaomi', 2000, 30, 3),
    ('LG K42', 'Smartphone da LG', 1500, 40, 3),
    ('Sony Bravia XBR-65X855D', 'TV 4K da Sony', 6000, 20, 4),
    ('Samsung QLED Q80T', 'TV QLED da Samsung', 5500, 15, 4),
    ('Philips 32PHG5813', 'TV Full HD da Philips', 1800, 25, 4),
    ('Camiseta básica branca', 'Camiseta básica de algodão', 30, 100, 5),
    ('Camiseta estampada', 'Camiseta estampada com desenhos', 35, 80, 5),
    ('Calça jeans masculina', 'Calça jeans para homens', 80, 70, 6),
    ('Calça legging feminina', 'Calça legging confortável', 50, 90, 6),
    ('Capa de silicone para iPhone', 'Capa protetora de silicone', 20, 200, 7),
    ('Capa rígida para Samsung Galaxy', 'Capa protetora rígida', 15, 150, 7),
    ('Fone de ouvido Bluetooth', 'Fone de ouvido sem fio', 100, 120, 8),
    ('Fone de ouvido com fio', 'Fone de ouvido convencional', 50, 180, 8),
    ('Smart TV LG 55UN7310PSC', 'TV 4K da LG', 3500, 10, 9),
    ('Smart TV Philips 43PUG6513', 'TV 4K da Philips', 2500, 15, 9),
    ('Conjunto de lingerie preta', 'Conjunto de lingerie feminina', 40, 60, 10),
    ('Cueca boxer masculina', 'Cueca boxer confortável', 25, 100, 10);




create table pessoa(
	id int primary key auto_increment,
    nome varchar(255) not null
);


create table usuario(
	id int primary key auto_increment,
    login varchar(50) not null,
    senha varchar(100) not null,
    pessoa_id int not null,
    constraint fk_pessoa 
		foreign key (pessoa_id) references pessoa(id)
);


INSERT INTO pessoa (nome) VALUES 
('João Silva'),
('Maria Oliveira'),
('Carlos Souza'),
('Ana Costa'),
('Pedro Fernandes'),
('Lucia Mendes'),
('Marcos Lima'),
('Fernanda Ribeiro'),
('José Almeida'),
('Paula Moreira');


INSERT INTO usuario (login, senha, pessoa_id) VALUES 
('joaosilva', 'senha123', 1),
('mariaoliveira', 'senha123', 2),
('carlossouza', 'senha123', 3),
('anacosta', 'senha123', 4),
('pedrofernandes', 'senha123', 5),
('luciamendes', 'senha123', 6),
('marcoslima', 'senha123', 7),
('fernandaribeiro', 'senha123', 8),
('josealmeida', 'senha123', 9),
('paulamoreira', 'senha123', 10);

