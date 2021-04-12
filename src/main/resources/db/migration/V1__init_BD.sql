create table hibernate_sequence
(
    next_val bigint
);
insert into hibernate_sequence values (36);

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

create table comment (
    id bigint not null,
    grade tinyint,
    text varchar(255),
    client_id bigint,
    master_id bigint,
    primary key (id)
);

create table grade (
    id bigint not null,
    grade tinyint,
    master_id bigint,
    primary key (id)
);

create table master_on_service (
    id bigint not null,
    master_id bigint,
    service_id bigint,
    primary key (id)
);

create table service (
    id bigint not null,
    duration bigint,
    name_en varchar(255),
    name_ua varchar(255),
    price integer,
    primary key (id)
);

create table timetable (
    id bigint not null,
    date_time datetime(6),
    done bit,
    paid bit not null,
    client_id bigint,
    master_on_service_id bigint,
    primary key (id)
);

alter table user_role
    add constraint user_role_user_fk
        foreign key (user_id) references usr (id);

alter table comment
    add constraint comment_client_fk
        foreign key (client_id) references usr (id);

alter table comment
    add constraint comment_master_fk
        foreign key (master_id) references usr (id);

alter table grade
    add constraint grade_master_fk
        foreign key (master_id) references usr (id);

alter table master_on_service
    add constraint master_on_service_master_fk
        foreign key (master_id) references usr (id);

alter table master_on_service
    add constraint master_on_service_service_fk
        foreign key (service_id) references service (id);

alter table timetable
    add constraint timetable_client_fk
        foreign key (client_id) references usr (id);

alter table timetable
    add constraint timetable_master_on_service_fk
        foreign key (master_on_service_id) references master_on_service (id);
