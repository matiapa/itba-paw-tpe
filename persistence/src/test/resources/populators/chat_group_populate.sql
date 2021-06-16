-- noinspection SyntaxErrorForFile

TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;
INSERT INTO career(code, name) VALUES ('S', 'Career 2');

INSERT  INTO users(id, name, email, signup_date, surname, profile_picture, career_code, verified, password) VALUES (0, 'TEST USER0', 'testemail0@gmail.com', default, 'TEST SURNAME0', null, 'S', true, '12345678');
INSERT  INTO users(id, name, email, signup_date, surname, profile_picture, career_code, verified, password) VALUES (1, 'TEST USER1', 'testemail1@gmail.com', default, 'TEST SURNAME1', null, 'S', true, '12345678');
INSERT  INTO users(id, name, email, signup_date, surname, profile_picture, career_code, verified, password) VALUES (2, 'TEST USER2', 'testemail2@gmail.com', default, 'TEST SURNAME2', null, 'S', true, '12345678');
INSERT  INTO users(id, name, email, signup_date, surname, profile_picture, career_code, verified, password) VALUES (3, 'TEST USER3', 'testemail3@gmail.com', default, 'TEST SURNAME3', null, 'S', true, '12345678');

INSERT INTO chat_group(name, link, submitted_by, platform, career_code) VALUES ('testName', 'testLink', 0, 'whatsapp', 'S');
