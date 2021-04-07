-- noinspection SyntaxError

TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO users(id, name, email, career_id,signup_date) VALUES (1, 'User 1', 'usr1@test.com', 1, default);
INSERT INTO users(id,name,email,career_id,signup_date) VALUES (2, 'User 2', 'usr2@test.com', 1, default);
