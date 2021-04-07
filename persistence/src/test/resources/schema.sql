-------------------- App-generated entities --------------------

CREATE TABLE career(
    id      int,
    name    varchar(100) not null,

    PRIMARY KEY (id)
);

CREATE TABLE course(
    id          varchar(100),
    name        varchar(100) not null,

    PRIMARY KEY (id)
);

CREATE TABLE career_course(
   career_id   int,
   course_id   varchar(100),

   PRIMARY KEY (career_id, course_id),
   FOREIGN KEY (career_id) REFERENCES career ON DELETE CASCADE,
   FOREIGN KEY (course_id) REFERENCES course ON DELETE CASCADE
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


-------------------- User-generated entities --------------------

CREATE TABLE chat_group(                -- Whatsapp/Discord
   id              int,
   career_id       int not null,
   creation_date   date not null,      -- Yr/Qt of the comission
   name            varchar(100) not null,
   link            varchar(100) not null,
   submitted_by    int not null,

   PRIMARY KEY (id),
   FOREIGN KEY (career_id) REFERENCES career ON DELETE CASCADE,
   FOREIGN KEY (submitted_by) REFERENCES users ON DELETE RESTRICT
);

CREATE TABLE content_source(            -- Drive/Reddit
   id              int,
   course_id       varchar(100) not null,
   name            varchar(100) not null,
   description     varchar(100) not null,
   link            varchar(100) not null,
   submitted_by    int not null,

   PRIMARY KEY (id),
   FOREIGN KEY (course_id) REFERENCES course ON DELETE CASCADE,
   FOREIGN KEY (submitted_by) REFERENCES users ON DELETE RESTRICT
);

CREATE TABLE announcement(
    id              int,
    title           varchar(100) not null,
    summary         varchar(100) not null,
    content         varchar(100) not null,
    career_id       int,
    course_id       varchar(100),
    creation_date   date not null default now(),
    expiry_date     date,
    submitted_by    int not null,

    PRIMARY KEY (id),
    FOREIGN KEY (career_id) REFERENCES career ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course ON DELETE CASCADE,
    FOREIGN KEY (submitted_by) REFERENCES users ON DELETE RESTRICT
);

CREATE TABLE poll(
    id              int generated always as IDENTITY(START WITH 1),
    name            varchar(100) not null,
    description     varchar(128),
    --format          ENUM('multiple-choice'),
    career_id       int,
    course_id       varchar(10),
    creation_date   timestamp without time zone default now not null,
    expiry_date     timestamp without time zone,
    submitted_by    int,

    PRIMARY KEY (id),
    FOREIGN KEY (career_id) REFERENCES career ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course ON DELETE CASCADE,
    FOREIGN KEY (submitted_by) REFERENCES users ON DELETE RESTRICT
);

CREATE TABLE poll_option(
    option_id       int generated always as IDENTITY(START WITH 1),
    poll_id         int not null,
    option_value    varchar(128),

    PRIMARY KEY (option_id),
    FOREIGN KEY (poll_id) REFERENCES poll ON DELETE CASCADE,
);