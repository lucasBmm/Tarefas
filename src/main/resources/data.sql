INSERT INTO departamento (id, titulo) VALUES (1, 'Financeiro');
INSERT INTO departamento (id, titulo) VALUES (2, 'Comercial');
INSERT INTO departamento (id, titulo) VALUES (3, 'Desenvolvimento');

INSERT INTO pessoa (id, id_departamento, nome) VALUES (1, 1, 'Camila');
INSERT INTO pessoa (id, id_departamento, nome) VALUES (2, 2, 'Pedro');
INSERT INTO pessoa (id, id_departamento, nome) VALUES (3, 3, 'Fabiano');
INSERT INTO pessoa (id, id_departamento, nome) VALUES (4, 3, 'Raquel');
INSERT INTO pessoa (id, id_departamento, nome) VALUES (5, 3, 'Patricia');
INSERT INTO pessoa (id, id_departamento, nome) VALUES (6, 1, 'Joaquim');

INSERT INTO tarefa (id, titulo, descricao, prazo, id_departamento, duracao, id_pessoa, finalizado) VALUES (1001, 'Validar NF Janeiro', 'Validar as notas recebidas no mês de Janeiro', '2022/02/15', 1, 14, 1, true);
INSERT INTO tarefa (id, titulo, descricao, prazo, id_departamento, duracao, id_pessoa, finalizado) VALUES (1002, 'Bug 352', 'Corrigir bug 352 na versão 1.25', '2022/05/10', 3, 25, null, false);
INSERT INTO tarefa (id, titulo, descricao, prazo, id_departamento, duracao, id_pessoa, finalizado) VALUES (1003, 'Liberação da versão 1.24', 'Disponibilizar pacote para testes' , '2022/02/02', 3, 2, 3, false);
INSERT INTO tarefa (id, titulo, descricao, prazo, id_departamento, duracao, id_pessoa, finalizado) VALUES (1004, 'Reunião A', 'Reunião com cliente A para apresentação do produto', '2022/02/05', 2, 5, null, false);
INSERT INTO tarefa (id, titulo, descricao, prazo, id_departamento, duracao, id_pessoa, finalizado) VALUES (1005, 'Reunião final', 'Fechamento contrato', '2022/03/28', 2, 6, 1, false);
INSERT INTO tarefa (id, titulo, descricao, prazo, id_departamento, duracao, id_pessoa, finalizado) VALUES (1006, 'Pagamento 01/2022', 'Realizar pagamento dos fornecedores',  '2022/01/31', 1, 6, 1, true);
INSERT INTO tarefa (id, titulo, descricao, prazo, id_departamento, duracao, id_pessoa, finalizado) VALUES (1007, 'Bug 401', 'Corrigir bur 401 na versão 1.20', '2022/02/01', 3, 2, 4, true);
INSERT INTO tarefa (id, titulo, descricao, prazo, id_departamento, duracao, id_pessoa, finalizado) VALUES (1008, 'Bug 399', 'Corrigir bug 399 na versão 1.20', '2022/01/28', 3, 6, 5, true);
INSERT INTO tarefa (id, titulo, descricao, prazo, id_departamento, duracao, id_pessoa, finalizado) VALUES (1009, 'Reunião B', 'Reunião com cliente B para apresentação do produto', '2022/01/31', 2, 5, 2, true);
INSERT INTO tarefa (id, titulo, descricao, prazo, id_departamento, duracao, id_pessoa, finalizado) VALUES (1010, 'Validar NF Fevereiro', 'Validar notas recebidas no mês de Fevereiro', '2022/03/15', 1, 14, 6, false);
