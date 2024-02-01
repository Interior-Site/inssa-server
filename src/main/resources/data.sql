INSERT INTO INSSA.user (created_date, modified_date, profile_no, quit_date, email, nickname, password, role, status) VALUES ('2023-08-08 17:10:00.971513', '2023-08-08 17:10:00.971513', null, null, 'test@gmail.com', 'test', '$2a$10$yR0K8xDdmi6PBQkX3LbtS.suEHurR/YDwI5Y9pesPfj27gBvHI73K', 'USER', 'ACTIVATED');
INSERT INTO INSSA.user (created_date, modified_date, profile_no, quit_date, email, nickname, password, role, status) VALUES ('2023-08-08 17:20:43.961376', '2023-08-08 17:20:43.961376', null, null, 'inssa@gmail.com', 'inssa', '$2a$10$iheJw9fZG6y/zCnzBWm1XuWUvgXvxohtM8KYTxw9qoj/pt7TtBynm', 'USER', 'ACTIVATED');
INSERT INTO INSSA.user (created_date, modified_date, profile_no, quit_date, email, nickname, password, role, status) VALUES ('2023-08-08 23:43:10.244423', '2023-08-08 23:43:10.244423', null, null, 'inssa@naver.com', '나무', '$2a$10$OTIJP2sxVAfl/TnMQRXqO.Sl6gmRjFaDBp1xFbJEFQVcwmmVuwmEK', 'USER', 'ACTIVATED');

INSERT INTO inssa.company (REGISTRATION_NO, COMPANY_NAME, CONTACT_NUMBER, STATUS, APPROVAL, RATING, USER_NO, created_date, modified_date) VALUES ('111', '회사test', '01011112222', 'Y', 'N', null, 2, now(), now());

INSERT INTO inssa.post (view_count, created_date, modified_date, status, type, title, content, user_no) VALUES (0, now(), now(), 'VISIBLE', 'NORMAL', 'test title', 'test content', 3);
INSERT INTO inssa.post (view_count, created_date, modified_date, status, type, title, content, user_no) VALUES (0, now(), now(), 'DELETED', 'NORMAL', 'test title', 'test content', 1);
INSERT INTO inssa.post (view_count, created_date, modified_date, status, type, title, content, user_no) VALUES (0, now(), now(), 'VISIBLE', 'NORMAL', 'test title', 'test content', 2);
INSERT INTO inssa.post (view_count, created_date, modified_date, status, type, title, content, user_no) VALUES (0, now(), now(), 'VISIBLE', 'NORMAL', 'test title', 'test content', 1);

INSERT INTO inssa.comment (comment_no, created_date, modified_date, parent_comment_no, post_no, user_no, content, status) VALUES (1, now(), now(), null, 1, 3, 'test comment', 'VISIBLE');
INSERT INTO inssa.comment (comment_no, created_date, modified_date, parent_comment_no, post_no, user_no, content, status) VALUES (2, now(), now(), null, 1, 2, 'test comment', 'VISIBLE');
INSERT INTO inssa.comment (comment_no, created_date, modified_date, parent_comment_no, post_no, user_no, content, status) VALUES (3, now(), now(), null, 1, 2, 'test comment', 'VISIBLE');
INSERT INTO inssa.comment (comment_no, created_date, modified_date, parent_comment_no, post_no, user_no, content, status) VALUES (4, now(), now(), null, 2, 2, 'test comment', 'VISIBLE');
INSERT INTO inssa.comment (comment_no, created_date, modified_date, parent_comment_no, post_no, user_no, content, status) VALUES (5, now(), now(), null, 2, 1, 'test comment', 'VISIBLE');
INSERT INTO inssa.comment (comment_no, created_date, modified_date, parent_comment_no, post_no, user_no, content, status) VALUES (6, now(), now(), null, 1, 1, 'test comment', 'VISIBLE');
INSERT INTO inssa.comment (comment_no, created_date, modified_date, parent_comment_no, post_no, user_no, content, status) VALUES (7, now(), now(), null, 1, 2, 'test comment', 'VISIBLE');
INSERT INTO inssa.comment (comment_no, created_date, modified_date, parent_comment_no, post_no, user_no, content, status) VALUES (8, now(), now(), null, 3, 1, 'test comment', 'VISIBLE');
INSERT INTO inssa.comment (comment_no, created_date, modified_date, parent_comment_no, post_no, user_no, content, status) VALUES (9, now(), now(), null, 3, 2, 'test comment', 'VISIBLE');
INSERT INTO inssa.comment (created_date, modified_date, parent_comment_no, post_no, user_no, content, status) VALUES (now(), now(), 9, 3, 3, 'test comment', 'VISIBLE');
INSERT INTO inssa.comment (created_date, modified_date, parent_comment_no, post_no, user_no, content, status) VALUES (now(), now(), 9, 3, 2, 'test comment', 'VISIBLE');
INSERT INTO inssa.comment (created_date, modified_date, parent_comment_no, post_no, user_no, content, status) VALUES (now(), now(), 9, 3, 3, 'test comment', 'DELETED');
INSERT INTO inssa.comment (created_date, modified_date, parent_comment_no, post_no, user_no, content, status) VALUES (now(), now(), 9, 3, 2, 'test comment', 'DELETED');
