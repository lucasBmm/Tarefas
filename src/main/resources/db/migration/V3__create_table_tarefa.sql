CREATE TABLE tarefa (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    prazo DATE,
    "idDepartamento" INT,
    duracao INT,
    "idPessoa" INT,
    finalizado BOOLEAN,
    FOREIGN KEY ("idDepartamento") REFERENCES departamento(id),
    FOREIGN KEY ("idPessoa") REFERENCES pessoa(id)
);