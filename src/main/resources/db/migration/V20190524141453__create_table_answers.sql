-- auto-generated definition
create table answers
(
    id          serial not null
        constraint answer_pk
            primary key,
    name        varchar,
    question_id integer
        constraint answer_question_id_fk
            references questions
            on update cascade on delete cascade,
    "right"     boolean
);

alter table answers
    owner to postgres;

create unique index answer_id_uindex
    on answers (id);

