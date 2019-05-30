create table selected_topics
(
    id serial not null,
    session_id character varying
        constraint selected_topics_quiz_session_session_id_fk
            references quiz_session
            on update cascade on delete cascade,
    topic_id int
        constraint selected_topics_topics_id_fk
            references topics
            on update cascade on delete cascade
);

create unique index selected_topics_id_uindex
    on selected_topics (id);

alter table selected_topics
    add constraint selected_topics_pk
        primary key (id);

