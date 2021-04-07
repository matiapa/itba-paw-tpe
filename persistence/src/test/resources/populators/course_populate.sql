-- noinspection SyntaxError

TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO career(id,name) VALUES (1, 'Career 1');
INSERT INTO career(id,name) VALUES (2, 'Career 1');

INSERT INTO course(id,name) VALUES ('1.1', 'Course 1');
INSERT INTO course(id,name) VALUES ('1.2', 'Course 2');

INSERT INTO career_course(career_id, course_id) VALUES (1, '1.1');
INSERT INTO career_course(career_id, course_id) VALUES (2, '1.2');

INSERT INTO users(id,name,email,career_id,signup_date) VALUES (1, 'User 1', 'usr1@test.com', 1, default);
INSERT INTO users(id,name,email,career_id,signup_date) VALUES (2, 'User 2', 'usr2@test.com', 1, default);

INSERT INTO fav_course(course_id, user_id) VALUES ('1.1', 1);
INSERT INTO fav_course(course_id, user_id) VALUES ('1.2', 2);