alter table selected_topics rename column session_id to quiz_session_id;

alter table selected_topics alter column quiz_session_id type int using quiz_session_id::int;

alter table selected_topics
    add constraint selected_topics_quiz_session_id_fk
        foreign key (quiz_session_id) references quiz_session
            on update cascade on delete cascade;

