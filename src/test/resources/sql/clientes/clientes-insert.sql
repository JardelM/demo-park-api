insert into USUARIOS (id, username, password, role) values (1500, 'teste@email.com', '$2a$12$ylehdkOMCgfpRaFVh7S3Cuefzpx/N9SV7EqsW4Sv8JTVuAlGM6TGG', 'ROLE_ADMIN');
insert into USUARIOS (id, username, password, role) values (1501, 'ana@email.com', '$2a$12$ylehdkOMCgfpRaFVh7S3Cuefzpx/N9SV7EqsW4Sv8JTVuAlGM6TGG', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (1502, 'beatriz@email.com', '$2a$12$ylehdkOMCgfpRaFVh7S3Cuefzpx/N9SV7EqsW4Sv8JTVuAlGM6TGG', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (1503, 'toby@email.com', '$2a$12$ylehdkOMCgfpRaFVh7S3Cuefzpx/N9SV7EqsW4Sv8JTVuAlGM6TGG', 'ROLE_CLIENTE');


insert into CLIENTES (id, nome, cpf, id_usuario) values (10, 'Ana Silva', '66651977008', 1501);
insert into CLIENTES (id, nome, cpf, id_usuario) values (20, 'Beatriz T', '55449017081', 1502)