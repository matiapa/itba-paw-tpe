-- noinspection SyntaxError

TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO users(id,name,surname,email,career_code,signup_date) VALUES (1, 'User 1', 'Surname', 'usr1@test.com', 1, default);
INSERT INTO users(id,name,surname,email,career_code,signup_date) VALUES (2, 'User 2', 'Surname', 'usr2@test.com', 1, default);