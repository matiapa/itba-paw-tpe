create sequence announcement_id_seq;

create sequence career_id_seq;

create sequence poll_id_seq;

create sequence poll_option_id_seq;

create type poll_format_type as enum ('multiple-choice', 'text');

create type action_type as enum ('create', 'read', 'update', 'delete');

create type chat_platform as enum ('whatsapp', 'discord');

create type entity_type as enum ('announcement', 'poll', 'course_content', 'chat_group', 'career', 'course', 'permission', 'user', 'statistic');

create type content_type_enum as enum ('exam', 'guide', 'exercise', 'project', 'theory', 'summary', 'bibliography', 'solution', 'code', 'miscellaneous');

create table career
(
    name varchar not null,
    code varchar not null
        constraint career_pkey
            primary key
        constraint career_code_key
            unique
);

create table course
(
    id      varchar not null
        constraint course_pkey
            primary key,
    name    varchar not null,
    credits integer not null
);

create table career_course
(
    course_id   varchar not null
        constraint career_course_course_id_fkey
            references course
            on delete cascade
        constraint career_courses_course_id_fkey
            references course
            on delete cascade,
    semester    integer,
    career_code varchar not null
        constraint career_course_career_code_fkey
            references career,
    constraint career_course_pkey
        primary key (course_id, career_code)
);

create table users
(
    id              integer               not null
        constraint users_pkey
            primary key,
    name            varchar               not null,
    email           varchar               not null,
    signup_date     date    default now() not null,
    surname         varchar,
    profile_picture varchar,
    career_code     varchar               not null
        constraint users_career_code_fkey
            references career,
    verified        boolean default false not null,
    password        varchar
);

create table fav_course
(
    course_id varchar not null
        constraint fav_course_course_id_fkey
            references course
            on delete cascade,
    user_id   integer not null
        constraint fav_course_user_id_fkey
            references users
            on delete cascade,
    constraint fav_course_pkey
        primary key (course_id, user_id)
);

create table chat_group
(
    creation_date date    not null,
    name          varchar,
    link          varchar not null,
    submitted_by  integer not null
        constraint chat_group_submitted_by_fkey
            references users
            on delete restrict,
    platform      varchar,
    id            serial  not null,
    career_code   varchar not null
        constraint chat_group_career_code_fkey
            references career
);

create table announcement
(
    id            integer default nextval('announcement_id_seq'::regclass) not null
        constraint announcement_pkey
            primary key,
    title         varchar                                                  not null,
    summary       varchar                                                  not null,
    content       varchar,
    course_id     varchar
        constraint announcement_course_id_fkey
            references course
            on delete cascade,
    creation_date date    default now()                                    not null,
    expiry_date   date,
    submitted_by  integer                                                  not null
        constraint announcement_submitted_by_fkey
            references users
            on delete restrict,
    career_code   varchar
        constraint announcement_career_code_fkey
            references career
);

create table poll
(
    id            integer   default nextval('poll_id_seq'::regclass) not null
        constraint poll_pkey
            primary key,
    name          varchar                                            not null,
    description   varchar,
    format        varchar                                            not null,
    course_id     varchar
        constraint poll_course_id_fkey
            references course
            on delete cascade,
    creation_date timestamp default now()                            not null,
    expiry_date   timestamp,
    submitted_by  integer                                            not null
        constraint poll_submitted_by_fkey
            references users
            on delete restrict,
    career_code   varchar
        constraint poll_career_code_fkey
            references career,
    link          varchar
);

create table poll_option
(
    poll_id      integer                                                 not null
        constraint poll_option_poll_id_fkey
            references poll
            on delete cascade,
    option_id    integer default nextval('poll_option_id_seq'::regclass) not null,
    option_value varchar,
    constraint poll_option_pkey
        primary key (poll_id, option_id)
);

create table poll_vote_registry
(
    user_id integer not null
        constraint poll_vote_registry_user_id_fkey
            references users
            on delete cascade,
    poll_id integer not null
        constraint poll_vote_registry_poll_id_fkey
            references poll
            on delete cascade,
    constraint poll_vote_registry_pkey
        primary key (user_id, poll_id)
);

create table poll_submission
(
    poll_id   integer                 not null
        constraint poll_submission_poll_id_fkey
            references poll
            on delete cascade,
    option_id integer,
    value     varchar,
    date      timestamp default now() not null,
    constraint poll_submission_poll_id_fkey1
        foreign key (poll_id, option_id) references poll_option
            on delete cascade,
    constraint poll_submission_poll_id_option_id_fkey
        foreign key (poll_id, option_id) references poll_option
            on delete cascade,
    constraint poll_submission_check
        check (((value IS NOT NULL) OR (option_id IS NOT NULL)) AND ((value IS NULL) OR (option_id IS NULL)))
);

create table permission
(
    action  varchar not null,
    user_id integer not null
        constraint permission_user_id_fkey
            references users
            on delete cascade,
    entity  varchar
);

create table announcement_seen
(
    announcement_id integer not null
        constraint announcement_seen_announcement_id_fkey
            references announcement
            on delete cascade,
    user_id         integer not null
        constraint announcement_seen_user_id_fkey
            references users,
    constraint announcement_seen_pkey
        primary key (announcement_id, user_id)
);

create table course_content
(
    name          varchar            not null,
    link          varchar            not null,
    submitted_by  integer            not null
        constraint content_source_submitted_by_fkey
            references users
            on delete set null,
    course_id     varchar            not null
        constraint content_source_fk1
            references course
            on delete cascade,
    description   varchar,
    creation_date date default now() not null,
    content_date  date,
    id            serial             not null,
    owner_email   varchar,
    content_type  varchar
);

create table user_verification
(
    user_id           integer not null
        constraint user_verification_pk
            primary key
        constraint user_id
            references users
            on delete cascade,
    verification_code integer not null
);

create table login_activity
(
    user_id integer                 not null
        constraint login_activity_user_id_fkey
            references users
            on delete restrict,
    date    timestamp default now() not null
);


