TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;

INSERT INTO career(id,name) VALUES (1, 'Career 1');

INSERT INTO course(id,name) VALUES ('1.1', 'Course 1');


INSERT INTO career_course(career_id, course_id) VALUES (1, '1.1');


INSERT INTO users(id,name,email,career_id,signup_date) VALUES (1, 'User 1', 'usr1@test.com', 1, default);


INSERT Into announcement(id,title,content,career_id,course_id,creation_date,expiry_date,submitted_by,summary) VALUES (1,'Test Title','Test content',1,'1.1','2021-03-01','2022-03-01',1,'Test summary');
INSERT Into announcement(id,title,content,career_id,course_id,creation_date,expiry_date,submitted_by,summary) VALUES (2,'Test Title2','Test content2',null,null,'2021-03-01','2022-03-01',1,'Test summary2');



