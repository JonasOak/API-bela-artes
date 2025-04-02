CREATE DATABASE belaArtes;

CREATE TABLE Usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    cargo ENUM('ADM', 'CLIENTE') NOT NULL
);
-- 
CREATE TABLE Cliente (
    id_cliente INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT UNIQUE NOT NULL,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,  
    telefone VARCHAR(20),
    logradouro VARCHAR(255),
    numero VARCHAR(10),
    bairro VARCHAR(100),
    cep VARCHAR(10),
    complemento VARCHAR(255),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE Produto (
    id_produto INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    categoria VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    url_foto VARCHAR(500) NOT NULL,
    estoque INT NOT NULL
);

CREATE TABLE Pedido (
    id_pedido INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente INT NOT NULL,
    data_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PENDENTE', 'PROCESSANDO', 'ENVIADO', 'CONCLUIDO', 'CANCELADO') NOT NULL DEFAULT 'PENDENTE',
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente) ON DELETE CASCADE
);

CREATE TABLE Item_Pedido (
    id_item INT PRIMARY KEY AUTO_INCREMENT,
    id_pedido INT NOT NULL,
    id_produto INT NOT NULL,
    quantidade INT NOT NULL CHECK (quantidade > 0),
    preco_unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_produto) REFERENCES Produto(id_produto) ON DELETE CASCADE
);

-- INSERÇÃO USUÁRIOS
INSERT INTO usuario (id_usuario, email, senha_hash, cargo) 
VALUES (1, 'cliente@email.com', 'senha123', 'CLIENTE');

-- INSERÇÃO CLIENTES
INSERT INTO cliente (id_cliente, id_usuario, nome, cpf, telefone, logradouro, numero, bairro, cep, complemento) 
VALUES (1, 1, 'João da Silva', '123.456.789-00', '11987654321', 'Rua das Flores', '123', 'Centro', '01010-000', 'Apto 12');

-- INSERÇÃO PEDIDOS
INSERT INTO pedido (id_pedido, id_cliente, data_pedido, status) 
VALUES (1, 1, NOW(), 'PENDENTE');