alter table quiz_answers rename column session_id to quiz_session_id;

alter table quiz_answers alter column quiz_session_id type int using quiz_session_id::int;

alter table quiz_answers
    add constraint quiz_answers_quiz_session_id_fk
        foreign key (quiz_session_id) references quiz_session
            on update cascade on delete cascade;

