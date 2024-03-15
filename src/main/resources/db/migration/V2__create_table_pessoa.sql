CREATE TABLE pessoa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    "idDepartamento" INT,
    FOREIGN KEY ("idDepartamento") REFERENCES departamento(id)
);