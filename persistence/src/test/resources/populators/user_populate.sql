-- noinspection SyntaxError

TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO career(code,name) VALUES ('A', 'Career 1');
INSERT INTO course(id,name,credits) VALUES ('1.1', 'Course 1',1);
INSERT INTO career_course(course_id,semester,career_code) VALUES ('1.1',1,'A');

INSERT INTO users(id,name,surname,email,career_code,signup_date,verified)
    VALUES (1, 'User 1', 'Surname', 'usr1@test.com', 'A', now(), false);
INSERT INTO users(id,name,surname,email,career_code,signup_date,verified)
    VALUES (2, 'User 2', 'Surname', 'usr2@test.com', 'A', now(), false);
