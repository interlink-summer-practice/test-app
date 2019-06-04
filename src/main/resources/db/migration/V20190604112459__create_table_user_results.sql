-- auto-generated definition
create table user_results
(
    id              serial  not null
        constraint user_results_pk
            primary key,
    user_id         integer not null
        constraint user_results_users_id_fk
            references users
            on update cascade on delete cascade,
    quiz_session_id integer not null
        constraint user_results_quiz_session_id_fk
            references quiz_session
            on update cascade on delete cascade,
    result          integer
);

alter table user_results
    owner to postgres;

create unique index user_results_id_uindex
    on user_results (id);

