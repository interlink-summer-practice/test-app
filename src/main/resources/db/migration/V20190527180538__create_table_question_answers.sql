create table question_answers
(
  question_id int not null
    constraint question_answers_questions_id_fk
      references questions
      on update cascade on delete cascade,
  answer_id int not null
    constraint question_answers_answers_id_fk
      references answers
      on update cascade on delete cascade
);