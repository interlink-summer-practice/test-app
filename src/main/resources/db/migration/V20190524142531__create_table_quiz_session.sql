-- auto-generated definition
create table quiz_session
(
    session_id varchar not null
        constraint sessions_pk
            primary key,
    user_id    integer
        constraint sessions_user_id_fk
            references users
            on update cascade on delete cascade,
    date       varchar
);

alter table quiz_session
    owner to postgres;

create unique index sessions_session_id_uindex
    on quiz_session (session_id);

