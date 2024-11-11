insert into USUARIOS (id, username, password, role) values (1500, 'teste@email.com', '$2a$12$ylehdkOMCgfpRaFVh7S3Cuefzpx/N9SV7EqsW4Sv8JTVuAlGM6TGG', 'ROLE_ADMIN');
insert into USUARIOS (id, username, password, role) values (1501, 'ana@email.com', '$2a$12$ylehdkOMCgfpRaFVh7S3Cuefzpx/N9SV7EqsW4Sv8JTVuAlGM6TGG', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (1502, 'beatriz@email.com', '$2a$12$ylehdkOMCgfpRaFVh7S3Cuefzpx/N9SV7EqsW4Sv8JTVuAlGM6TGG', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (1503, 'toby@email.com', '$2a$12$ylehdkOMCgfpRaFVh7S3Cuefzpx/N9SV7EqsW4Sv8JTVuAlGM6TGG', 'ROLE_CLIENTE');


insert into CLIENTES (id, nome, cpf, id_usuario) values (10, 'Ana Silva', '66651977008', 1501);
insert into CLIENTES (id, nome, cpf, id_usuario) values (20, 'Beatriz T', '55449017081', 1502);

insert into VAGAS (id, codigo, status) values (10, 'A-01', 'OCUPADA');
insert into VAGAS (id, codigo, status) values (20, 'A-02', 'OCUPADA');
insert into VAGAS (id, codigo, status) values (30, 'A-03', 'OCUPADA');

insert into CLIENTES_TEM_VAGAS (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    values ('20230313-101300', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2023-03-13 10:13:00', 10, 10);
insert into CLIENTES_TEM_VAGAS (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    values ('20230314-101300', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2023-03-14 10:13:00', 20, 20);
insert into CLIENTES_TEM_VAGAS (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    values ('20230315-101300', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2023-03-15 10:13:00', 10, 30);