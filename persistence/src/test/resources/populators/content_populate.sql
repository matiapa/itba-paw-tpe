-- noinspection SyntaxErrorForFile

TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO career(id,name) VALUES (1, 'Career 1');
INSERT INTO career(id,name) VALUES (2, 'Career 2');

INSERT INTO course(id,name) VALUES ('1.1', 'Course 1');
INSERT INTO course(id,name) VALUES ('1.2', 'Course 2');

INSERT INTO users(id,name,surname,email,career_id,signup_date) VALUES (1, 'User 1', 'Surname', 'usr1@test.com', 1, default);
INSERT INTO users(id,name,surname,email,career_id,signup_date) VALUES (2, 'User 2', 'Surname', 'usr2@test.com', 1, default);

INSERT INTO course_content(id,name,description,link,submitted_by,course_id) VALUES (1, 'Content 1', '', 'http://test.com', 1, '1.1');
INSERT INTO course_content(id,name,description,link,submitted_by,course_id) VALUES (2, 'Content 2', '', 'http://test.com', 1, '1.2');