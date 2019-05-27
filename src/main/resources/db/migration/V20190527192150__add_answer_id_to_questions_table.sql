alter table questions
  add answer_id int not null;

alter table questions
  add constraint questions_answers_id_fk
    foreign key (answer_id) references answers
      on update cascade on delete cascade;