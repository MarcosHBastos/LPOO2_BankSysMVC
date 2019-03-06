use bd_trablpoo2;

DROP TABLE cliente;


CREATE TABLE cliente (
id_cliente int PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(100),
sobrenome VARCHAR(100),
rg VARCHAR(10),
cpf VARCHAR(11),
endereco VARCHAR(100),
renda double,
tipo_conta CHAR(1),
CONSTRAINT ukcpf UNIQUE KEY (cpf));

CREATE TABLE conta (
numero_conta int PRIMARY KEY AUTO_INCREMENT, -- conta corrente
cpf VARCHAR(11),
saldo double,
limite double, 				 	 -- conta corrente
deposito_minimo double,			 -- conta investimento
montante_minimo double,			 -- conta investimento
tipo_conta CHAR(1),
CONSTRAINT FOREIGN KEY (cpf) REFERENCES cliente (cpf));

ALTER TABLE conta AUTO_INCREMENT = 1001;
ALTER TABLE cliente AUTO_INCREMENT = 5001;

