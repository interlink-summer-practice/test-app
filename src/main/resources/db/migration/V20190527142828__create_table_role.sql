create table role
(
    id serial not null,
    name character varying not null
);

create unique index role_id_uindex
    on role (id);

alter table role
    add constraint role_pk
        primary key (id);

