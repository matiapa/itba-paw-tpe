-- noinspection SyntaxErrorForFile

TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;

----------------------------------------------------------------------------------------------------------

-- There will be 12 contributions, 9 new and 3 old

INSERT INTO career(code,name) VALUES ('A', 'Career 1'); -- Will have 1 contribution
INSERT INTO career(code,name) VALUES ('B', 'Career 2'); -- Will have 2 contributions
INSERT INTO career(code,name) VALUES ('C', 'Career 3'); -- Will have 3 contributions

INSERT INTO course(id,name,credits) VALUES ('1.1', 'Course 1', 1); -- Will have 1 contribution
INSERT INTO course(id,name,credits) VALUES ('1.2', 'Course 2', 1); -- Will have 2 contributions
INSERT INTO course(id,name,credits) VALUES ('1.3', 'Course 3', 1); -- Will have 3 contributions

INSERT INTO users(id,name,surname,email,career_code,verified,signup_date)
    VALUES (1, 'User 1', 'Surname', 'usr1@test.com', 'A', true, now()); -- Will have 2 contributions
INSERT INTO users(id,name,surname,email,career_code,verified,signup_date)
    VALUES (2, 'User 2', 'Surname', 'usr2@test.com', 'A', true, now()); -- Will have 4 contributions
INSERT INTO users(id,name,surname,email,career_code,verified,signup_date)
    VALUES (3, 'User 3', 'Surname', 'usr3@test.com', 'A', true, now()); -- Will have 6 contributions

INSERT INTO login_activity(id, user_id, date) VALUES (1, 1, '2021-05-02');

INSERT INTO announcement(id, title, content, summary, submitted_by, career_code, course_id, creation_date) VALUES
(1, 'Test', 'Test', 'Test', 1 , 'A', null, '2021-05-03'),
(2, 'Test', 'Test', 'Test', 2 , 'B', null, '2021-05-03'),
(3, 'Test', 'Test', 'Test', 2 , null, '1.1', '2021-05-01');

INSERT INTO chat_group(id, name, link, platform, submitted_by, career_code, creation_date) VALUES
(1, 'Test', 'Test', 'whatsapp', 1, 'B', '2021-05-03'),
(2, 'Test', 'Test', 'whatsapp', 2, 'C', '2021-05-03'),
(3, 'Test', 'Test', 'whatsapp', 2, 'C', '2021-05-01');

INSERT INTO course_content(id, name, link, description, content_type, submitted_by, course_id, creation_date) VALUES
(1, 'Test', 'Test', 'Test', 'exam', 3, '1.2', '2021-05-03'),
(2, 'Test', 'Test', 'Test', 'exam', 3, '1.2', '2021-05-03'),
(3, 'Test', 'Test', 'Test', 'exam', 3, '1.3', '2021-05-01');

INSERT INTO poll(id, name, description, format, submitted_by, career_code, course_id, creation_date) VALUES
(1, 'Test', 'Test', 'text', 3, 'C', null, '2021-05-03'),
(2, 'Test', 'Test', 'text', 3, null, '1.3', '2021-05-03'),
(3, 'Test', 'Test', 'text', 3, null, '1.3', '2021-05-03');