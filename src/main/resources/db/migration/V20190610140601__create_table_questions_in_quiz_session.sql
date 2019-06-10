create table question_in_quiz_session
(
    quiz_session_id int
        constraint question_in_quiz_session_quiz_session_id_fk
            references quiz_session
            on update cascade on delete cascade,
    question_id int
        constraint question_in_quiz_session_questions_id_fk
            references questions
            on update cascade on delete cascade
);


