create table hibernate_sequence
(
    next_val bigint
);
insert into hibernate_sequence
values (37);

create table comment
(
    id        bigint not null,
    grade     tinyint,
    text      varchar(255),
    client_user_id bigint,
    master_user_id bigint,
    primary key (id)
);

create table master
(
    grade   float,
    user_id bigint not null,
    primary key (user_id)
);

create table master_services
(
    user_id    bigint not null,
    service_id bigint not null
);

create table service
(
    service_id bigint not null,
    duration   bigint,
    name_en    varchar(255),
    name_ua    varchar(255),
    price      integer,
    primary key (service_id)
);

create table timetable
(
    id                 bigint not null,
    date_time          datetime(6),
    done               bit,
    paid               bit    not null,
    client_user_id     bigint,
    master_user_id     bigint,
    service_service_id bigint,
    primary key (id)
);

create table user_role
(
    user_id bigint not null,
    role    varchar(255)
);

create table usr
(
    user_id         bigint not null,
    activation_code varchar(255),
    active          bit    not null,
    email           varchar(255),
    password        varchar(255),
    username        varchar(255),
    name            varchar(255),
    surname         varchar(255),
    primary key (user_id)
);

alter table comment
    add constraint comment_client_fk
        foreign key (client_user_id) references usr (user_id);

alter table comment
    add constraint comment_master_fk
        foreign key (master_user_id) references usr (user_id);

alter table master
    add constraint master_user_fk
        foreign key (user_id) references usr (user_id);

alter table master_services
    add constraint master_services_service_fk
        foreign key (service_id) references service (service_id);

alter table master_services
    add constraint master_services_master_fk
        foreign key (user_id) references master (user_id);

alter table timetable
    add constraint timetable_client_fk
        foreign key (client_user_id) references usr (user_id);

alter table timetable
    add constraint timetable_master_fk
        foreign key (master_user_id) references master (user_id);

alter table timetable
    add constraint timetable_service_fk
        foreign key (service_service_id) references service (service_id);

alter table user_role
    add constraint user_role_user_fk
        foreign key (user_id) references usr (user_id);
