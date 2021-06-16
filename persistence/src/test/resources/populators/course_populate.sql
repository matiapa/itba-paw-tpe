-- noinspection SyntaxError
TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO career(code,name) VALUES ('A', 'Career 1');

INSERT INTO course(id,name,credits) VALUES ('1.1', 'Course 1', 1);

INSERT INTO career_course(course_id,semester,career_code) VALUES ('1.1',1,'A');

INSERT INTO users(id,name,surname,email,career_code,verified,signup_date) VALUES (1, 'User 1', 'Surname', 'usr1@test.com', 'A', true,now());

INSERT INTO fav_course (course_id,user_id) VALUES ('1.1',1);

INSERT INTO course_review(id,review, submitted_by, course_id,creation_date) VALUES(0,'test_review', 1, '1.1', now());
