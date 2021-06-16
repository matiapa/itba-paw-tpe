-- noinspection SyntaxErrorForFile

TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK;



INSERT INTO career(name,code) VALUES ('Ingeniería en Informática','S');



INSERT INTO course(id,name,credits) VALUES ('1.1','Física III',0);


INSERT INTO users(id,name,surname,email,career_code,signup_date,verified,password) VALUES (1, 'User 1', 'Surname', 'usr1@test.com', 'S', now(),true,'dummypass');


INSERT INTO course_content(id,name,description,link,submitted_by,course_id,creation_date,content_type,content_date) VALUES (0,'Content 1', 'desc', 'http://test.com', 1, '1.1','2020-01-05','miscellaneous',now());
INSERT INTO course_content(id,name,description,link,submitted_by,course_id,creation_date,content_type,content_date) VALUES (1,'Content 2', 'desc', 'http://test.com', 1, '1.1','2020-01-04','miscellaneous',now());

INSERT INTO content_review(id,review,submitted_by,content_id,creation_date)VALUES (0,'test review',1,0,now());

INSERT INTO fav_course(course_id,user_id)VALUES ('1.1',1);
