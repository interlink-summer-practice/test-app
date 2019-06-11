create table group_members
(
    group_id int not null
        constraint group_members_groups_id_fk
            references groups
            on update cascade on delete cascade,
    user_id int not null
        constraint group_members_users_id_fk
            references users
            on update cascade on delete cascade
);
