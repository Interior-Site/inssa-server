INSERT INTO inssa.build_type (NAME) VALUES ('아파트');
INSERT INTO inssa.build_type (NAME) VALUES ('빌라');
INSERT INTO inssa.build_type (NAME) VALUES ('오피스텔');
INSERT INTO inssa.build_type (NAME) VALUES ('주상복합');
INSERT INTO inssa.build_type (NAME) VALUES ('단독주택');

INSERT INTO inssa.category (NAME) VALUES ('벽지');
INSERT INTO inssa.category (NAME) VALUES ('도배');
INSERT INTO inssa.category (NAME) VALUES ('장판');
INSERT INTO inssa.category (NAME) VALUES ('샷시');
INSERT INTO inssa.category (NAME) VALUES ('줄눈');
INSERT INTO inssa.category (NAME) VALUES ('확장');
INSERT INTO inssa.category (NAME) VALUES ('타일');
INSERT INTO inssa.category (NAME) VALUES ('벽지');

INSERT INTO INSSA.user (created_date, modified_date, profile_no, quit_date, email, nickname, password, role, status) VALUES ('2023-08-08 17:10:00.971513', '2023-08-08 17:10:00.971513', null, null, 'test@gmail.com', 'test', '$2a$10$ggkeXY7KA3LwcoACzSF2OO/ev.A4HwWaTMeniLczQ/v.AZFrQWq3W', 'USER', 'ACTIVATED');
INSERT INTO INSSA.user (created_date, modified_date, profile_no, quit_date, email, nickname, password, role, status) VALUES ('2023-08-08 17:20:43.961376', '2023-08-08 17:20:43.961376', null, null, 'inssa@gmail.com', 'inssa', '$2a$10$aitoVPlyQWr2glas8wj5CO7En.h19kob7DW0KPNDCQcydynEkpqMS', 'USER', 'ACTIVATED');
INSERT INTO INSSA.user (created_date, modified_date, profile_no, quit_date, email, nickname, password, role, status) VALUES ('2023-08-08 23:43:10.244423', '2023-08-08 23:43:10.244423', null, null, 'inssa@naver.com', 'inssa-test', '$2a$10$arx.W4d17jykL5zvQGrgkOdQfgJ/O.e11HkcfAsIQrJuEKFwRQ8Cm', 'USER', 'ACTIVATED');

INSERT INTO inssa.company (REGISTRATION_NO, COMPANY_NAME, CONTACT_NUMBER, STATUS, APPROVAL, RATING, USER_NO) VALUES ('111', '회사test', '01011112222', 'Y', 'N', null, 1);
