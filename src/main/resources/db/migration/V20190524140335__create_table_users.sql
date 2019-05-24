-- auto-generated definition
create table users
(
    id            serial not null
        constraint user_pk
            primary key,
    first_name    varchar,
    last_name     varchar,
    email         varchar,
    password_hash varchar
);

alter table users
    owner to postgres;

create unique index user_id_uindex
    on users (id);

