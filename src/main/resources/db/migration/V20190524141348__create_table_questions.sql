-- auto-generated definition
create table questions
(
    id         serial  not null
        constraint question_pk
            primary key,
    name       varchar not null,
    difficulty varchar not null,
    mark       integer not null,
    topic_id   integer
        constraint questions_topics_id_fk
            references topics
            on update cascade on delete cascade
);

alter table questions
    owner to postgres;

create unique index question_id_uindex
    on questions (id);

