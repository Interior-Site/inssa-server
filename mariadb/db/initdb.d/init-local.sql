INSERT INTO INSSA.user (created_date, modified_date, profile_no, quit_date, email, nickname, password, role, status) VALUES ('2023-08-08 17:10:00.971513', '2023-08-08 17:10:00.971513', null, null, 'test@gmail.com', 'test', '$2a$10$ggkeXY7KA3LwcoACzSF2OO/ev.A4HwWaTMeniLczQ/v.AZFrQWq3W', 'USER', 'ACTIVATED');
INSERT INTO INSSA.user (created_date, modified_date, profile_no, quit_date, email, nickname, password, role, status) VALUES ('2023-08-08 17:20:43.961376', '2023-08-08 17:20:43.961376', null, null, 'inssa@gmail.com', 'inssa', '$2a$10$aitoVPlyQWr2glas8wj5CO7En.h19kob7DW0KPNDCQcydynEkpqMS', 'USER', 'ACTIVATED');
INSERT INTO INSSA.user (created_date, modified_date, profile_no, quit_date, email, nickname, password, role, status) VALUES ('2023-08-08 23:43:10.244423', '2023-08-08 23:43:10.244423', null, null, 'inssa@naver.com', '나무', '$2a$10$09.e19cKkNDSqzlXM6DkquKq5hfZwcM87BdViS3giy/y9.aFqDAsq', 'USER', 'ACTIVATED');

INSERT INTO inssa.company (REGISTRATION_NO, COMPANY_NAME, CONTACT_NUMBER, STATUS, APPROVAL, RATING, USER_NO) VALUES ('111', '회사test', '01011112222', 'Y', 'N', null, 2);

INSERT INTO inssa.post (created_date, modified_date, status, type) VALUES (now(), now(), 'VISIBLE', 'ARTICLE');
INSERT INTO inssa.post (created_date, modified_date, status, type) VALUES (now(), now(), 'VISIBLE', 'REVIEW');
INSERT INTO inssa.post (created_date, modified_date, status, type) VALUES (now(), now(), 'VISIBLE', 'REVIEW');