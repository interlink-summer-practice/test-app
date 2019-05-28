alter table quiz_answers
  add question_id int not null;

alter table quiz_answers
  add constraint quiz_answers_questions_id_fk
    foreign key (question_id) references questions
      on update cascade on delete cascade;