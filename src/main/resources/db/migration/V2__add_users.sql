insert into usr (id, username, password, active, email)
values(1, 'master1', 'pass', true, 'master1@gmail.com');
insert into user_role (user_id, roles)
values (1, 'MASTER');

insert into usr (id, username, password, active, email)
values(2, 'master2', 'pass', true, 'master2@gmail.com');
insert into user_role (user_id, roles)
values (2, 'MASTER');

insert into usr (id, username, password, active, email)
values(3, 'client1', 'pass', true, 'client1@gmail.com');
insert into user_role (user_id, roles)
values (3, 'CLIENT');

insert into usr (id, username, password, active, email)
values(4, 'client', 'pass', true, 'client@gmail.com');
insert into user_role (user_id, roles)
values (4, 'CLIENT');

insert into usr (id, username, password, active, email)
values(5, 'admin', 'pass', true, 'admin@gmail.com');
insert into user_role (user_id, roles)
values (5, 'ADMIN');