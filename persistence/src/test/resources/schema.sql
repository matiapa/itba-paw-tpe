create view career_contribs(name, code, contribs) as
SELECT career.*, (
    SELECT sum(c) FROM (
       SELECT 'a', count(*) c FROM announcement WHERE career_code=career.code
       UNION SELECT 'p', count(*) c FROM poll WHERE career_code=career.code
       UNION SELECT 'g', count(*) c FROM chat_group WHERE career_code=career.code
    ) AS cs
) contribs FROM career ORDER BY contribs DESC LIMIT 5;

create view daily_contribs(date, contribs) as
SELECT creation_date date, count(*) contribs FROM (
  SELECT 'a', id, creation_date FROM announcement
  UNION SELECT 'p', id, creation_date FROM poll
  UNION SELECT 'g', id, creation_date FROM chat_group
  UNION SELECT 'c', id, creation_date FROM course_content
) contributions GROUP BY creation_date ORDER BY creation_date;

create view top_users_contribs(id, name, email, signup_date, surname, profile_picture, career_code,
    verified, password, contribs) as
SELECT users.*, (
    SELECT SUM(c) FROM (
       SELECT 'a', count(*) c FROM announcement WHERE submitted_by=users.id
       UNION SELECT 'p', count(*) c FROM poll WHERE submitted_by=users.id
       UNION SELECT 'g', count(*) c FROM chat_group WHERE submitted_by=users.id
       UNION SELECT 'c', count(*) c FROM course_content WHERE submitted_by=users.id
   ) AS cs
) contribs FROM users ORDER BY contribs DESC LIMIT 10;

create view top_courses_contribs(id, name, credits, contribs) as
SELECT course.*, (
    SELECT SUM(c) FROM (
       SELECT 'a', count(*) c FROM announcement WHERE course_id=course.id
       UNION SELECT 'p', count(*) c FROM poll WHERE course_id=course.id
       UNION SELECT 'c', count(*) c FROM course_content WHERE course_id=course.id
   ) AS contribs
) contribs FROM course ORDER BY contribs DESC LIMIT 10;
