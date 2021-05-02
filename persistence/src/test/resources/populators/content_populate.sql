
TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;



INSERT INTO career(name,code) VALUES ('Ingeniería en Informática','S');



INSERT INTO course(id,name,credits) VALUES ('93.43','Física III',null);


INSERT INTO users(id,name,surname,email,career_code,signup_date,verified,password) VALUES (1, 'User 1', 'Surname', 'usr1@test.com', 1, default,true,'dummypass');


INSERT INTO course_content(id,name,description,link,submitted_by,course_id,creation_date,content_type,content_date) VALUES (1, 'Content 1', 'desc', 'http://test.com', 1, '93.45',default,'other',2020-10-14);
INSERT INTO course_content(id,name,description,link,submitted_by,course_id,creation_date,content_type,content_date) VALUES (2, 'Content 2', 'desc', 'http://test.com', 1, '93.45',default,'other',2020-10-14);
