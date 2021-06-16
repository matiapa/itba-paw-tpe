TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;
SET TIME ZONE INTERVAL '00:00' HOUR TO MINUTE;

INSERT INTO career(name, code) VALUES ('Career 1', 'S');
INSERT INTO career(name, code) VALUES ('Career 2', 'T');
INSERT INTO career(name, code) VALUES ('Career 3', 'U');
INSERT INTO career(name, code) VALUES ('Career 4', 'V');

INSERT INTO course(id,name,credits) VALUES ('1.1', 'Course 1',1);

INSERT INTO career_course(course_id,semester,career_code) VALUES ('1.1',1,'S');

INSERT INTO users(id,name,surname,email,career_code,signup_date, verified)
VALUES
(1, 'User 1', 'Surname', 'usr1@test.com', 'S', now(), true),
(2, 'User 2', 'Surname', 'usr1@test.com', 'S', now(), true),
(3, 'User 3', 'Surname', 'usr1@test.com', 'S', now(), true),
(4, 'User 4', 'Surname', 'usr1@test.com', 'S', now(), true);

INSERT INTO poll(id, name, description, career_code, course_id, expiry_date, submitted_by, format, creation_date)
VALUES
(1, 'Name 1', null             , null, null, null, 1, 'multiple_choice', now()),
(2, 'Name 2', 'Description 2'  , null, null, null, 1, 'multiple_choice', now()),
(3, 'Name 3', 'Description 3'  , 'S', null, null, 1, 'multiple_choice', now()),
(4, 'Name 4', 'Description 4'  , null, '1.1', null, 1, 'multiple_choice', now()),
(5, 'Name 5', 'Description 5'  , null, null, '1970-01-01 00:00:00', 1, 'multiple_choice', now()),
(6, 'Name 6', 'Description 6'  , null, null, null, 1, 'multiple_choice', now()),
(7, 'Name 7', 'Description 7'  , null, '1.1', '2900-05-08 00:00:00', 1, 'multiple_choice', now());

INSERT INTO poll_option(option_id, poll_id, option_value)
VALUES
(1, 7, 'Option 1'),
(2, 7, 'Option 2'),
(3, 7, 'Option 3');

INSERT INTO poll_submission(id, poll_id, option_id, value, date)
VALUES
(1, 7, 1, null, now()),
(2, 7, 1, null, now());

INSERT INTO poll_vote_registry(user_id, poll_id)
VALUES
(1, 7),
(2, 7);