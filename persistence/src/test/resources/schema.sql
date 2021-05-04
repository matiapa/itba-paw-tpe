create sequence announcement_id_seq;

create sequence career_id_seq;

create sequence poll_id_seq;

create sequence poll_option_id_seq;

create table career
(
    name text not null,
    code text not null,
    constraint career_pkey
    primary key (code)
);

create table course
(
    id      text not null,
    name    text not null,
    credits integer,
    constraint course_pkey
        primary key (id)
);

create table career_course
(
    course_id   text not null,
    semester    integer,
    career_code text not null,
    constraint career_course_pkey
        primary key (course_id, career_code),
    constraint career_course_course_id_fkey
        foreign key (course_id) references course
            on delete cascade,

    constraint career_course_career_code_fkey
        foreign key (career_code) references career
);

create table users
(
    id              integer               not null,
    name            text               not null,
    email           text               not null,
    signup_date     date    default now() not null,
    surname         text,
    profile_picture text,
    career_code     text               not null,
    verified        boolean default false not null,
    password        text,
    constraint users_pkey
        primary key (id),
    constraint users_career_code_fkey
        foreign key (career_code) references career
);

create table fav_course
(
    course_id text not null,
    user_id   integer not null,
    constraint fav_course_pkey
        primary key (course_id, user_id),
    constraint fav_course_course_id_fkey
        foreign key (course_id) references course
            on delete cascade,
    constraint fav_course_user_id_fkey
        foreign key (user_id) references users
            on delete cascade
);

create table chat_group
(
    creation_date date    not null,
    name          text,
    link          text not null,
    submitted_by  integer not null,
    platform      text check (platform in ('whatsapp', 'discord')),
    id            serial  not null,
    career_code   text not null,
    constraint chat_group_submitted_by_fkey
        foreign key (submitted_by) references users
            on delete restrict,
    constraint chat_group_career_code_fkey
        foreign key (career_code) references career
);

create table announcement
(
    id            integer generated by default as sequence announcement_id_seq not null,
    title         text                                                  not null,
    summary       text                                                  not null,
    content       text                                                  not null,
    course_id     text,
    creation_date date    default now()                                    not null,
    expiry_date   date,
    submitted_by  integer                                                  not null,
    career_code   text,
    constraint announcement_pkey
        primary key (id),
    constraint announcement_course_id_fkey
        foreign key (course_id) references course
            on delete cascade,
    constraint announcement_submitted_by_fkey
        foreign key (submitted_by) references users
            on delete restrict,
    constraint announcement_career_code_fkey
        foreign key (career_code) references career
);

create table poll
(
    id            integer   generated by default as sequence poll_id_seq not null,
    name          text                                            not null,
    description   text,
    format        text                                            not null   check (format in ('multiple-choice', 'text')),
    course_id     text,
    creation_date timestamp default now()                            not null,
    expiry_date   timestamp,
    submitted_by  integer                                            not null,
    career_code   text,
    constraint poll_pkey
        primary key (id),
    constraint poll_course_id_fkey
        foreign key (course_id) references course
            on delete cascade,
    constraint poll_submitted_by_fkey
        foreign key (submitted_by) references users
            on delete restrict,
    constraint poll_career_code_fkey
        foreign key (career_code) references career
);

create table poll_option
(
    poll_id      integer                                                 not null,
    option_id    integer generated by default as sequence poll_option_id_seq not null,
    option_value text,
    constraint poll_option_pkey
        primary key (poll_id, option_id),
    constraint poll_option_poll_id_fkey
        foreign key (poll_id) references poll
            on delete cascade
);

create table poll_vote_registry
(
    user_id integer not null,
    poll_id integer not null,
    constraint poll_vote_registry_pkey
        primary key (user_id, poll_id),
    constraint poll_vote_registry_user_id_fkey
        foreign key (user_id) references users
            on delete cascade,
    constraint poll_vote_registry_poll_id_fkey
        foreign key (poll_id) references poll
            on delete cascade
);

create table poll_submission
(
    poll_id   integer                 not null,
    option_id integer,
    value     text,
    date      timestamp default now() not null,
    constraint poll_submission_poll_id_fkey
        foreign key (poll_id) references poll
            on delete cascade,
    constraint poll_submission_poll_id_fkey1
        foreign key (poll_id, option_id) references poll_option
            on delete cascade,

    constraint poll_submission_check
        check (((value IS NOT NULL) OR (option_id IS NOT NULL)) AND ((value IS NULL) OR (option_id IS NULL)))
);

create table permission
(
    action  text not null check (action in ('create', 'read', 'update', 'delete')),
    user_id integer     not null,
    entity  text check (entity in ('announcement', 'poll', 'course_content', 'chat_group', 'career', 'course', 'permission', 'user', 'statistic')),
    constraint permission_user_id_fkey
        foreign key (user_id) references users
            on delete cascade
);

create table announcement_seen
(
    announcement_id integer not null,
    user_id         integer not null,
    constraint announcement_seen_pkey
        primary key (announcement_id, user_id),
    constraint announcement_seen_announcement_id_fkey
        foreign key (announcement_id) references announcement,
    constraint announcement_seen_user_id_fkey
        foreign key (user_id) references users
);

create table course_content
(
    name          text            not null,
    link          text            not null,
    submitted_by  integer,
    course_id     text            not null,
    description   text            not null,
    creation_date date default now() not null,
    content_type  text            check (content_type in ('exam', 'guide', 'exercise', 'project', 'theory', 'summary', 'bibliography', 'solution', 'code', 'miscellaneous')),
    content_date  date,
    id            serial             not null,
    constraint content_source_fk1
        foreign key (course_id) references course
            on delete cascade,
    constraint content_source_submitted_by_fkey
        foreign key (submitted_by) references users
            on delete set null
);

create table user_verification
(
    user_id           integer not null,
    verification_code integer not null,
    constraint user_verification_pk
        primary key (user_id),
    constraint user_id
        foreign key (user_id) references users
            on delete cascade
);

create table login_activity
(
    user_id integer                 not null,
    date    timestamp default now() not null,
    constraint login_activity_user_id_fkey
        foreign key (user_id) references users
            on delete restrict
);