TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;
SET TIME ZONE INTERVAL '00:00' HOUR TO MINUTE;

INSERT INTO career(id,name) VALUES (1, 'Career 1');
INSERT INTO course(id,name) VALUES ('1.1', 'Course 1');
INSERT INTO career_course(career_id, course_id) VALUES (1, '1.1');
INSERT INTO users(id,name,surname,email,career_id,signup_date) VALUES (1, 'User 1', 'Surname', 'usr1@test.com', 1, default);

INSERT INTO poll(name, description, career_id, course_id, expiry_date, submitted_by)
VALUES 
('Name 1', null, null, null, null, null),
('Name 2', 'Description 2', null, null, null, null),
('Name 3', 'Description 3', 1, null, null, null),
('Name 4', 'Description 4', 1, '1.1', null, null),
('Name 5', 'Description 5', null, null, '2021-05-08 00:00:00', null),
('Name 6', 'Description 6', null, null, null, 1),
('Name 7', 'Description 7', 1, '1.1', '2021-05-08 00:00:00', 1);

INSERT INTO poll_option(poll_id, option_value)
VALUES
(7, 'Option 1'),
(7, 'Option 2'),
(7, 'Option 3');