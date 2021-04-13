-- all password are '123456'

insert into usr (user_id, username, name, surname, password, active, email)
values(1, 'master1', "Irina", "Fedishin",
       '$2a$08$2LOikFNRlwGKguS6XRqpFOsvoFu4GAzT34akOydPcBBZYo8rV0sdm', true, 'master1@gmail.com');
insert into user_role (user_id, role)
values (1, 'MASTER');
insert into master (user_id, grade)
values (1, 4);

insert into usr (user_id, username, name, surname, password, active, email)
values(2, 'master2', "Leo", "Nardo",
       '$2a$08$2LOikFNRlwGKguS6XRqpFOsvoFu4GAzT34akOydPcBBZYo8rV0sdm', true, 'master2@gmail.com');
insert into user_role (user_id, role)
values (2, 'MASTER');
insert into master (user_id, grade)
values (2, 5);

insert into usr (user_id, username, name, surname, password, active, email)
values(3, 'client1', "Галя", "Петренко",
       '$2a$08$2LOikFNRlwGKguS6XRqpFOsvoFu4GAzT34akOydPcBBZYo8rV0sdm', true, 'client1@gmail.com');
insert into user_role (user_id, role)
values (3, 'CLIENT');

insert into usr (user_id, username, name, surname, password, active, email)
values(4, 'client', "Mila", "Mina",
       '$2a$08$2LOikFNRlwGKguS6XRqpFOsvoFu4GAzT34akOydPcBBZYo8rV0sdm', true, 'client@gmail.com');
insert into user_role (user_id, role)
values (4, 'CLIENT');

insert into usr (user_id, username, name, surname, password, active, email)
values(5, 'admin', "", "",
       '$2a$08$2LOikFNRlwGKguS6XRqpFOsvoFu4GAzT34akOydPcBBZYo8rV0sdm', true, 'admin@gmail.com');
insert into user_role (user_id, role)
values (5, 'ADMIN');

insert into usr (user_id, username, name, surname, password, active, email)
values(36, 'client2', "", "",
       '$2a$08$2LOikFNRlwGKguS6XRqpFOsvoFu4GAzT34akOydPcBBZYo8rV0sdm', true, 'client2@gmail.com');
insert into user_role (user_id, role)
values (36, 'CLIENT');