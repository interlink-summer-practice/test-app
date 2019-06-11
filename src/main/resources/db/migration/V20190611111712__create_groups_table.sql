create table groups
(
    id serial not null
        constraint groups_pk
            primary key,
    name character varying not null,
    quiz_url character varying not null,
    curator_id int not null
        constraint groups_users_id_fk
            references users
            on update cascade on delete cascade
);

