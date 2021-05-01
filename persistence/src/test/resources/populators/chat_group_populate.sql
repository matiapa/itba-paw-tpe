-- noinspection SyntaxErrorForFile

TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO career(id, name) VALUES (1, 'Informatica');

INSERT INTO users(id,name,surname,email,career_code,signup_date) VALUES (1, 'User 1', 'Surname', 'usr1@test.com', 1, default);
INSERT INTO users(id,name,surname,email,career_code,signup_date) VALUES (2, 'User 2', 'Surname', 'usr2@test.com', 1, default);

INSERT INTO chat_group(id, career_code, creation_date, name, link, submitted_by) VALUES (1, 1, '2020-11-10', 'Whatsapp', 'linkWhatsapp', 1);
INSERT INTO chat_group(id, career_code, creation_date, name, link, submitted_by) VALUES (2, 1, '2011-11-10', 'Discord', 'linkDiscord', 2);
INSERT INTO chat_group(id, career_code, creation_date, name, link, submitted_by) VALUES (3, 1, '2018-11-20', 'Reddit', 'linkReddit', 1);


