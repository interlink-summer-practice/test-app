alter table topics
    add subtopic_id int;

alter table topics
    add constraint topics_topics_id_fk
        foreign key (subtopic_id) references topics
            on update cascade on delete cascade;