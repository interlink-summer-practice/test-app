create table user_roles
(
    id serial not null,
    user_id int
        constraint user_roles_users_id_fk
            references users
            on update cascade on delete cascade,
    role_id int
        constraint user_roles_role_id_fk
            references role
            on update cascade on delete cascade
);

create unique index user_roles_id_uindex
    on user_roles (id);

alter table user_roles
    add constraint user_roles_pk
        primary key (id);

