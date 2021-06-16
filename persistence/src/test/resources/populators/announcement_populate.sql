-- noinspection SyntaxErrorForFile

TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;


INSERT INTO career(code,name) VALUES ('A', 'Career 1');

INSERT INTO course(id,name) VALUES ('1.1', 'Course 1');

INSERT INTO users(id,name,surname,email,career_code,verified) VALUES (0, 'User 1', 'Surname', 'usr1@test.com', 'A', true);

INSERT INTO announcement(title,content,career_code,course_id,submitted_by,summary) VALUES
    ('Test Title', 'Test content', 'S', '1.1' , 0, 'Test summary'),
    ('Test Title', 'Test content', 'A', '1.1' , 0, 'Test summary'),
    ('Test Title', 'Test content', 'A', '1.1' , 0, 'Test summary'),
    ('Test Title', 'Test content', 'A', '1.1' , 0, 'Test summary'),
    ('Test Title', 'Test content', 'A', '1.1' , 0, 'Test summary'),
    ('Test Title', 'Test content', 'A', '1.1' , 0, 'Test summary');

INSERT INTO announcement_seen(announcement_id, user_id) VALUES (6, 0);



