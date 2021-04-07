create table hibernate_sequence
(
    next_val bigint
);
insert into hibernate_sequence values (2);

create table user_role (
    user_id bigint not null,
    roles varchar(255)
);

create table usr (
    id bigint not null,
    activation_code varchar(255),
    active bit not null,
    email varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
);

alter table user_role
    add constraint user_role_user_fk
        foreign key (user_id) references usr (id);