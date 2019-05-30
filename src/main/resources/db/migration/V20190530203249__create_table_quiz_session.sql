create table quiz_session
(
    id serial not null,
    session_id character varying not null,
    user_id int
        constraint quiz_session_users_id_fk
            references users
            on update cascade on delete cascade,
    date character varying
);

create unique index quiz_session_id_uindex
    on quiz_session (id);

alter table quiz_session
    add constraint quiz_session_pk
        primary key (id);

