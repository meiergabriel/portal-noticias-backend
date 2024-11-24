package br.com.guilhermevillaca.loja.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DBInsert implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        try {
            // Desabilitar verificações de chave estrangeira
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

            // Deletar tabelas, se existirem
            entityManager.createNativeQuery("DROP TABLE IF EXISTS comentario").executeUpdate();
            entityManager.createNativeQuery("DROP TABLE IF EXISTS noticia").executeUpdate();
            entityManager.createNativeQuery("DROP TABLE IF EXISTS categoria").executeUpdate();
            entityManager.createNativeQuery("DROP TABLE IF EXISTS usuario").executeUpdate();
            entityManager.createNativeQuery("DROP TABLE IF EXISTS permissao").executeUpdate();

            // Habilitar verificações de chave estrangeira novamente
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

            // Criar tabelas
            entityManager.createNativeQuery("""
                CREATE TABLE usuario (
                    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
                    nome VARCHAR(100) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    senha VARCHAR(100) NOT NULL,
                    tipo ENUM('VISITANTE', 'EDITOR', 'ADMINISTRADOR') NOT NULL,
                    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """).executeUpdate();

            entityManager.createNativeQuery("""
                CREATE TABLE categoria (
                    id_categoria INT PRIMARY KEY AUTO_INCREMENT,
                    nome VARCHAR(50) UNIQUE NOT NULL,
                    descricao TEXT
                )
            """).executeUpdate();

            entityManager.createNativeQuery("""
                CREATE TABLE noticia (
                    id_noticia INT PRIMARY KEY AUTO_INCREMENT,
                    titulo VARCHAR(200) NOT NULL,
                    corpo TEXT NOT NULL,
                    data_publicacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    imagem_url VARCHAR(255),
                    id_categoria INT,
                    id_editor INT,
                    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria) ON DELETE CASCADE,
                    FOREIGN KEY (id_editor) REFERENCES usuario(id_usuario) ON DELETE SET NULL
                )
            """).executeUpdate();

            entityManager.createNativeQuery("""
                CREATE TABLE comentario (
                    id_comentario INT PRIMARY KEY AUTO_INCREMENT,
                    texto TEXT NOT NULL,
                    data_comentario TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    id_noticia INT,
                    id_usuario INT,
                    FOREIGN KEY (id_noticia) REFERENCES noticia(id_noticia) ON DELETE CASCADE,
                    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
                )
            """).executeUpdate();

            entityManager.createNativeQuery("""
                CREATE TABLE permissao (
                    id_permissao INT PRIMARY KEY AUTO_INCREMENT,
                    tipo_usuario ENUM('VISITANTE', 'EDITOR', 'ADMINISTRADOR') NOT NULL,
                    permissao VARCHAR(50) NOT NULL
                )
            """).executeUpdate();

            // Inserir categorias
            entityManager.createNativeQuery("""
                INSERT INTO categoria (nome, descricao) VALUES 
                ('Política', 'Notícias e atualizações sobre política nacional e internacional.'),
                ('Esportes', 'Cobertura de eventos esportivos e resultados de campeonatos.'),
                ('Tecnologia', 'Notícias sobre tecnologia, inovações e lançamentos de produtos.'),
                ('Entretenimento', 'Novidades sobre filmes, séries, celebridades e cultura pop.'),
                ('Economia', 'Informações e análises sobre economia, finanças e negócios.')
            """).executeUpdate();

            // Inserir usuários
            entityManager.createNativeQuery("""
                INSERT INTO usuario (nome, email, senha, tipo, data_criacao) VALUES 
                ('Alice Santos', 'alice.santos@example.com', 'senha123', 'VISITANTE', CURRENT_TIMESTAMP),
                ('Bruno Almeida', 'bruno.almeida@example.com', 'senha123', 'VISITANTE', CURRENT_TIMESTAMP),
                ('Carlos Pereira', 'carlos.pereira@example.com', 'senha123', 'EDITOR', CURRENT_TIMESTAMP),
                ('Daniela Costa', 'daniela.costa@example.com', 'senha123', 'EDITOR', CURRENT_TIMESTAMP),
                ('Eduardo Martins', 'eduardo.martins@example.com', 'senha123', 'ADMINISTRADOR', CURRENT_TIMESTAMP),
                ('Fernanda Silva', 'fernanda.silva@example.com', 'senha123', 'ADMINISTRADOR', CURRENT_TIMESTAMP)
            """).executeUpdate();

            // Inserir permissões
            entityManager.createNativeQuery("""
                INSERT INTO permissao (tipo_usuario, permissao) VALUES
                ('VISITANTE', 'LER_NOTICIA'),
                ('VISITANTE', 'COMENTAR_NOTICIA'),
                ('EDITOR', 'LER_NOTICIA'),
                ('EDITOR', 'CRIAR_NOTICIA'),
                ('EDITOR', 'EDITAR_NOTICIA'),
                ('EDITOR', 'REMOVER_COMENTARIO'),
                ('ADMINISTRADOR', 'LER_NOTICIA'),
                ('ADMINISTRADOR', 'CRIAR_NOTICIA'),
                ('ADMINISTRADOR', 'EDITAR_NOTICIA'),
                ('ADMINISTRADOR', 'REMOVER_NOTICIA'),
                ('ADMINISTRADOR', 'GERENCIAR_USUARIOS'),
                ('ADMINISTRADOR', 'REMOVER_COMENTARIO')
            """).executeUpdate();

            // Inserir notícias
            entityManager.createNativeQuery("""
                INSERT INTO noticia (titulo, corpo, data_publicacao, imagem_url, id_categoria, id_editor) VALUES
                ('Avanços na Inteligência Artificial', 'A inteligência artificial tem avançado rapidamente em várias áreas...', CURRENT_TIMESTAMP, 'https://example.com/images/ia.jpg', 3, 3),
                ('Novas Descobertas no Campo da Medicina', 'Pesquisadores descobriram uma nova técnica para tratar doenças...', CURRENT_TIMESTAMP, 'https://example.com/images/medicina.jpg', 1, 4),
                ('Tecnologia 5G Expande-se pelo Mundo', 'A tecnologia 5G está se espalhando rapidamente, trazendo novas possibilidades...', CURRENT_TIMESTAMP, 'https://example.com/images/5g.jpg', 3, 3),
                ('Esportes Olímpicos 2024: Expectativas para o Brasil', 'O Brasil tem grandes chances nas próximas Olimpíadas...', CURRENT_TIMESTAMP, 'https://example.com/images/esportes.jpg', 2, 4),
                ('Mercado Financeiro em Alta', 'O mercado financeiro apresenta alta histórica, impulsionado pela tecnologia...', CURRENT_TIMESTAMP, 'https://example.com/images/financeiro.jpg', 5, 3)
            """).executeUpdate();

            System.out.println("----------All Data saved into Database----------------------");
        } catch (Exception e) {
            System.err.println("Erro ao executar script: " + e.getMessage());
            e.printStackTrace();
        }
    }
}