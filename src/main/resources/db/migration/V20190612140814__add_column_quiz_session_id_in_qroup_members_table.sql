alter table group_members
    add quiz_session_id int;

alter table group_members
    add constraint group_members_quiz_session_id_fk
        foreign key (quiz_session_id) references quiz_session
            on update cascade on delete cascade;
