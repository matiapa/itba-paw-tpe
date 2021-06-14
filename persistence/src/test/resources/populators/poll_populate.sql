TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;
SET TIME ZONE INTERVAL '00:00' HOUR TO MINUTE;

INSERT INTO career(name, code) VALUES ('Career 1', 'S');
INSERT INTO career(name, code) VALUES ('Career 2', 'T');
INSERT INTO career(name, code) VALUES ('Career 3', 'U');
INSERT INTO career(name, code) VALUES ('Career 4', 'V');

INSERT INTO course(id,name) VALUES ('1.1', 'Course 1');

INSERT INTO career_course(course_id,semester,career_code) VALUES ('1.1',1,'S');

INSERT INTO users(id,name,surname,email,career_code)
VALUES 
(1, 'User 1', 'Surname', 'usr1@test.com', 'S'),
(2, 'User 2', 'Surname', 'usr1@test.com', 'S'),
(3, 'User 3', 'Surname', 'usr1@test.com', 'S'),
(4, 'User 4', 'Surname', 'usr1@test.com', 'S');

INSERT INTO poll(name, description, career_code, course_id, expiry_date, submitted_by)
VALUES 
('Name 1', null             , null, null, null, null),
('Name 2', 'Description 2'  , null, null, null, null),
('Name 3', 'Description 3'  , 'S', null, null, null),
('Name 4', 'Description 4'  , null, '1.1', null, null),
('Name 5', 'Description 5'  , null, null, '1970-01-01 00:00:00', null),
('Name 6', 'Description 6'  , null, null, null, 1),
('Name 7', 'Description 7'  , null, '1.1', '2900-05-08 00:00:00', 1);

INSERT INTO poll_option(poll_id, option_value)
VALUES
(7, 'Option 1'),
(7, 'Option 2'),
(7, 'Option 3');

INSERT INTO poll_submission(poll_id, option_id, value)
VALUES
(7, 1, null),
(7, 1, null),
(7, 2, null);

INSERT INTO poll_vote_registry(user_id, poll_id)
VALUES
(1, 7),
(2, 7);