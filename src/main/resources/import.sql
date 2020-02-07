INSERT INTO student (id, first_name, last_name) VALUES (1, 'Edy', 'Huiza');
INSERT INTO student (id, first_name, last_name) VALUES (2, 'Juan', 'Huiza');
INSERT INTO student (id, first_name, last_name) VALUES (3, 'Daniel', 'Arrancibia');

INSERT INTO class (code, title, description) VALUES ('12345678', 'Algebra Lineal', 'Ecuaciones diferenciales, ecuaciones, etc');
INSERT INTO class (code, title, description) VALUES ('12345679', 'Calculo I', 'Limites, derivadas, integrales, etc');

INSERT INTO student_class (student, code_class) VALUES (1, '12345678');
INSERT INTO student_class (student, code_class) VALUES (1, '12345679');
INSERT INTO student_class (student, code_class) VALUES (2, '12345678');