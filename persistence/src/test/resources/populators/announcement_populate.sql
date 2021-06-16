-- noinspection SyntaxErrorForFile

TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO career(code,name) VALUES ('A', 'Career 1');

INSERT INTO course(id,name,credits) VALUES ('1.1', 'Course 1',1);

INSERT INTO users(id,name,surname,email,career_code,verified,signup_date) VALUES
    (1, 'User 1', 'Surname', 'usr1@test.com', 'A', true, now());

INSERT INTO announcement(id,title,content,career_code,course_id,submitted_by,summary,creation_date) VALUES
    (1,'Test Title', 'Test content', null, null , 1, 'Test summary', now()),
    (2,'Test Title', 'Test content', 'A' , null , 1, 'Test summary', now()),
    (3,'Test Title', 'Test content', null, '1.1', 1, 'Test summary', now()),
    (4,'Test Title', 'Test content', null, null , 1, 'Test summary', now()),
    (5,'Test Title', 'Test content', null, null , 1, 'Test summary', now()),
    (6,'Test Title', 'Test content', null, null , 1, 'Test summary', now());

INSERT INTO announcement_seen(announcement_id, user_id) VALUES (6, 1);

INSERT INTO fav_course(course_id, user_id) VALUES ('1.1', 1);

