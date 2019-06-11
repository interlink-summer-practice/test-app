create table difficulty_in_quiz_session
(
    quiz_session_id int not null
        constraint difficulty_in_quiz_session_quiz_session_id_fk
            references quiz_session
            on update cascade on delete cascade,
    difficulty character varying not null
);