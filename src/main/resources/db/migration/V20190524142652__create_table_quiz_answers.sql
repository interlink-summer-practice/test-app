-- auto-generated definition
create table quiz_answers
(
    id         serial not null
        constraint quiz_answers_pk
            primary key,
    session_id varchar
        constraint quiz_answers_sessions_session_id_fk
            references quiz_session
            on update cascade on delete cascade,
    answer_id  integer
        constraint quiz_answers_answer_id_fk
            references answers
            on update cascade on delete cascade
);

alter table quiz_answers
    owner to postgres;

create unique index quiz_answers_id_uindex
    on quiz_answers (id);

