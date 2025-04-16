-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           8.0.30 - MySQL Community Server - GPL
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              12.8.0.6908
-- --------------------------------------------------------

CREATE DATABASE IF NOT EXISTS `belaartes`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;
USE `belaartes`;

-- Tabela cliente
CREATE TABLE IF NOT EXISTS `cliente` (
  `id_cliente` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `cpf` VARCHAR(14) NOT NULL,
  `telefone` VARCHAR(20),
  `logradouro` VARCHAR(255),
  `numero` VARCHAR(10),
  `bairro` VARCHAR(100),
  `cep` VARCHAR(10),
  `complemento` VARCHAR(255),
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `cpf` (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabela produto (ajustada para armazenar imagem)
CREATE TABLE IF NOT EXISTS `produto` (
  `id_produto` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `descricao` TEXT,
  `categoria` VARCHAR(100) NOT NULL,
  `preco` DECIMAL(10,2) NOT NULL,
  `imagem` LONGBLOB NOT NULL,
  `estoque` INT NOT NULL,
  PRIMARY KEY (`id_produto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabela pedido
CREATE TABLE IF NOT EXISTS `pedido` (
  `id_pedido` INT NOT NULL AUTO_INCREMENT,
  `id_cliente` INT NOT NULL,
  `data_pedido` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `status` ENUM('PENDENTE','PROCESSANDO','ENVIADO','CONCLUIDO','CANCELADO') NOT NULL DEFAULT 'PENDENTE',
  PRIMARY KEY (`id_pedido`),
  KEY `id_cliente` (`id_cliente`),
  CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`id_cliente`)
    REFERENCES `cliente` (`id_cliente`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabela item_pedido
CREATE TABLE IF NOT EXISTS `item_pedido` (
  `id_item_pedido` INT NOT NULL AUTO_INCREMENT,
  `id_pedido` INT NOT NULL,
  `id_produto` INT NOT NULL,
  `quantidade` INT NOT NULL,
  `preco_unitario` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id_item_pedido`),
  KEY `id_pedido` (`id_pedido`),
  KEY `id_produto` (`id_produto`),
  CONSTRAINT `item_pedido_ibfk_1` FOREIGN KEY (`id_pedido`)
    REFERENCES `pedido` (`id_pedido`) ON DELETE CASCADE,
  CONSTRAINT `item_pedido_ibfk_2` FOREIGN KEY (`id_produto`)
    REFERENCES `produto` (`id_produto`) ON DELETE RESTRICT,
  CONSTRAINT `item_pedido_chk_1` CHECK ((`quantidade` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabela usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `senha_hash` VARCHAR(255) NOT NULL,
  `cargo` ENUM('ADM','CLIENTE') NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;