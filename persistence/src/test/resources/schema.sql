CREATE TABLE career(
    id      int,
    name    varchar(100) not null,

    PRIMARY KEY (id)
);

CREATE TABLE course(
    id          varchar(100),
    name        varchar(100) not null,
    career_id   int not null,

    PRIMARY KEY (id),
    FOREIGN KEY (career_id) REFERENCES career ON DELETE RESTRICT
);

CREATE TABLE users(
    id          int,
    name        varchar(100) not null,
    email       varchar(100) not null,
    career_id   int not null,
    signup_date date not null default now(),

    PRIMARY KEY (id),
    FOREIGN KEY (career_id) REFERENCES career ON DELETE RESTRICT
);

CREATE TABLE fav_course(
    course_id   varchar(100),
    user_id     int,

    PRIMARY KEY (course_id, user_id),
    FOREIGN KEY (course_id) REFERENCES course ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users ON DELETE CASCADE
);

CREATE TABLE chat_group(
    id              int,
    career_id       int not null,
    submitted_by    int,
    creation_date   date not null,
    link            varchar not null,

    PRIMARY KEY (id),
    FOREIGN KEY (career_id) REFERENCES career ON DELETE CASCADE,
    FOREIGN KEY (submitted_by) REFERENCES users ON DELETE SET NULL
);