-- auto-generated definition
create table topics
(
    id   serial  not null
        constraint topics_pk
            primary key,
    name varchar not null
);

alter table topics
    owner to postgres;

create unique index topics_id_uindex
    on topics (id);

