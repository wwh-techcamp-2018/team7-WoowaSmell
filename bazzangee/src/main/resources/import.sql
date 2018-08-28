insert into user (user_id, password, name, phone_number) values
('serverwizard@naver.com', '$2a$10$xfi04lvpOR5J2H2aTINQ2e08V/n9Xjc9XWTDUz2Ry03zDbk4y/a4a', '자바지기', '012-1233-1233'),
('serverwizard2@naver.com', '$2a$10$xfi04lvpOR5J2H2aTINQ2e08V/n9Xjc9XWTDUz2Ry03zDbk4y/a4a', '자바지기2', '013-1233-1233'),
('kwonhyeona@naver.com', '$2a$10$xfi04lvpOR5J2H2aTINQ2e08V/n9Xjc9XWTDUz2Ry03zDbk4y/a4a', '권현아', '010-1234-1234'),
('songminseok@naver.com', '$2a$10$xfi04lvpOR5J2H2aTINQ2e08V/n9Xjc9XWTDUz2Ry03zDbk4y/a4a', '송민석', '010-4321-4321'),
('parkjuha@naver.com', '$2a$10$xfi04lvpOR5J2H2aTINQ2e08V/n9Xjc9XWTDUz2Ry03zDbk4y/a4a', '박주하', '010-1111-2222');

insert into food_category (name, priority) values
('치킨', 1),
('피자', 2),
('한식', 3),
('중국집', 4),
('분식', 5),
('족발.보쌈', 6),
('돈까스.회.일식', 7),
('야식', 8);

insert into chat (name) values
('치킨'),
('피자'),
('한식'),
('중국집'),
('분식'),
('족발.보쌈'),
('돈까스.회.일식'),
('야식'),
('전체');


insert into restaurant (address, food_category_id, name) values ('잠실 송파점', 1, '굽네치킨');
insert into restaurant (address, food_category_id, name) values ('잠실 방이점', 2, '네네피자');
insert into restaurant (address, food_category_id, name) values ('잠실 정육점', 3, '포비양식');
insert into restaurant (address, food_category_id, name) values ('잠실 점점점', 4, '도미노한식');
insert into restaurant (address, food_category_id, name) values ('부천점', 5, '길림성');
insert into restaurant (address, food_category_id, name) values ('주하점', 6, '토도로끼');
insert into restaurant (address, food_category_id, name) values ('현아점', 7, '크롱네떡볶이');
insert into restaurant (address, food_category_id, name) values ('종완점', 8, '버거킹');
insert into restaurant (address, food_category_id, name) values ('삼성생명', 2, '파파존스피자');
insert into restaurant (address, food_category_id, name) values ('잠실롯데타워', 1, '맘스터치');

insert into food (name, restaurant_id) values ('싸이버거', 8);
insert into food (name, restaurant_id) values ('후라이드', 1);
insert into food (name, restaurant_id) values ('하와이안피자', 2);
insert into food (name, restaurant_id) values ('간장치킨', 1);
insert into food (name, restaurant_id) values ('포비포테이토피자', 2);
insert into food (name, restaurant_id) values ('볼케이노치킨', 1);
insert into food (name, restaurant_id) values ('치즈버거', 8);
insert into food (name, restaurant_id) values ('삼겹살', 4);
insert into food (name, restaurant_id) values ('짜장면', 5);
insert into food (name, restaurant_id) values ('탕수육', 5);
insert into food (name, restaurant_id) values ('떡볶이', 7);
insert into food (name, restaurant_id) values ('소바', 6);
insert into food (name, restaurant_id) values ('김밥', 4);
insert into food (name, restaurant_id) values ('파스타', 3);

insert into order_food (food_id, quantity, ordered_user_id, order_time) values (1, 7, 1, '2018-05-12 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (2, 2, 1, '2018-05-22 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (3, 3, 1, '2018-05-12 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (4, 10, 1, '2018-05-05 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (5, 6, 2, '2018-05-17 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (6, 10, 2, '2018-05-12 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (7, 20, 2, '2018-05-08 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (8, 12, 2, '2018-05-07 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (9, 1, 2, '2018-05-28 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (10, 1, 2, '2018-06-30 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (11, 2, 2, '2018-08-02 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (12, 2, 2, '2018-09-02 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (13, 1, 2, '2018-10-05 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (14, 1, 2, '2018-10-05 20:00:00');

insert into review (title, food_category_id, contents, image_url, star_point, written_time, updated_time, order_food_id, user_id) values ('교촌치킨', 8, '치킨하면 허니콤보!!!진짜 맛있어요ㅠㅠㅠ내일 또 먹어야지', 'https://s3.ap-northeast-2.amazonaws.com/baezzangee/static/default/default_chicken.png', 4, '2018-05-30 20:00:00', '2018-05-30 20:00:00', 1, 2);
insert into review (title, food_category_id, contents, image_url, star_point, written_time, updated_time, order_food_id, user_id) values ('네네치킨', 1, '치킨하면 네네치킨!!!진짜 맛있어요ㅠㅠㅠ내일 또 먹어야지', 'https://s3.ap-northeast-2.amazonaws.com/baezzangee/static/default/default_chicken.png', 4, '2018-06-20 20:00:00', '2018-06-20 20:00:00', 2, 2);
insert into review (title, food_category_id, contents, image_url, star_point, written_time, updated_time, order_food_id, user_id) values ('석촌치킨', 2, '치킨하면 석촌치킨ㅋㅋㅋ진짜 맛있어요ㅠㅠㅠ내일 또 먹어야지', 'https://s3.ap-northeast-2.amazonaws.com/baezzangee/static/default/default_chicken.png', 5, '2018-05-08 20:00:00', '2018-05-08 20:00:00', 3, 2);
insert into review (title, food_category_id, contents, image_url, star_point, written_time, updated_time, order_food_id, user_id) values ('방이치킨', 1, '치킨이 진짜 맛있었다ㅠㅠㅠㅠㅠ후라이드가 제일 맛있어요!!', 'https://s3.ap-northeast-2.amazonaws.com/baezzangee/static/default/default_chicken.png', 4, '2018-08-02 20:00:00', '2018-08-02 20:00:00', 4, 2);
insert into review (title, food_category_id, contents, image_url, star_point, written_time, updated_time, order_food_id, user_id) values ('잠실치킨', 2, '진짜 별로ㅋㅋㅋㅋㅋㅋ;;;다시는 안먹어야지', 'https://s3.ap-northeast-2.amazonaws.com/baezzangee/static/default/default_chicken.png', 2.5, '2018-05-03 20:00:00', '2018-05-03 20:00:00', 5, 2);
insert into review (title, food_category_id, contents, image_url, star_point, written_time, updated_time, order_food_id, user_id) values ('종완치킨', 1, '별로예요ㅠㅠㅠ이거보고 절대 시키지 마세요.', 'https://s3.ap-northeast-2.amazonaws.com/baezzangee/static/default/default_chicken.png', 1.5, '2018-07-02 20:00:00', '2018-07-02 20:00:00', 6, 2);
insert into review (title, food_category_id, contents, image_url, star_point, written_time, updated_time, order_food_id, user_id) values ('맥날버거', 8, '다시는 안먹어ㅠㅠㅠㅠ내돈', 'https://s3.ap-northeast-2.amazonaws.com/baezzangee/static/default/default_chicken.png', 1, '2018-05-19 20:00:00', '2018-05-19 20:00:00', 7, 2);
insert into review (title, food_category_id, contents, image_url, star_point, written_time, updated_time, order_food_id, user_id) values ('롯데버거', 4, '짱이에요', 'https://s3.ap-northeast-2.amazonaws.com/baezzangee/static/default/default_chicken.png', 5, '2018-05-12 20:00:00', '2018-05-12 20:00:00', 8, 2);
insert into review (title, food_category_id, contents, image_url, star_point, written_time, updated_time, order_food_id, user_id) values ('크롱버거', 5, '괜찮아요', 'https://s3.ap-northeast-2.amazonaws.com/baezzangee/static/default/default_chicken.png', 4.5, '2018-01-21 20:00:00', '2018-01-21 20:00:00', 9, 2);
insert into review (title, food_category_id, contents, image_url, star_point, written_time, updated_time, order_food_id, user_id) values ('포비버거', 5, '먹을만해요', 'https://s3.ap-northeast-2.amazonaws.com/baezzangee/static/default/default_chicken.png', 3.5, '2018-05-01 20:00:00', '2018-05-01 20:00:00', 10, 2);
insert into review (title, food_category_id, contents, image_url, star_point, written_time, updated_time, order_food_id, user_id) values ('주하버거', 7, '또먹을래요', 'https://s3.ap-northeast-2.amazonaws.com/baezzangee/static/default/default_chicken.png', 4.5, '2018-02-02 20:00:00', '2018-02-02 20:00:00', 11, 2);
insert into review (title, food_category_id, contents, image_url, star_point, written_time, updated_time, order_food_id, user_id) values ('교촌피자', 6, '허니콤보 맛잇어요', 'https://s3.ap-northeast-2.amazonaws.com/baezzangee/static/default/default_chicken.png', 4, '2018-05-03 20:00:00', '2018-05-03 20:00:00', 12, 2);
insert into review (title, food_category_id, contents, image_url, star_point, written_time, updated_time, order_food_id, user_id) values ('주하피자', 4, '또먹을래요', 'https://s3.ap-northeast-2.amazonaws.com/baezzangee/static/default/default_chicken.png',4.5, '2018-02-12 20:00:00', '2018-02-12 20:00:00', 13, 2);

insert into good (user_id, review_id) values (1, 2);
insert into good (user_id, review_id) values (2, 3);
insert into good (user_id, review_id) values (1, 4);
insert into good (user_id, review_id) values (2, 5);
insert into good (user_id, review_id) values (1, 5);
insert into good (user_id, review_id) values (1, 6);
insert into good (user_id, review_id) values (2, 6);
insert into good (user_id, review_id) values (1, 6);
insert into good (user_id, review_id) values (2, 9);
insert into good (user_id, review_id) values (1, 10);
insert into good (user_id, review_id) values (1, 11);



insert into chat_message (user_id, chat_id, contents, written_time) values (1, 9, '자바지기 채팅!!!', '2018-02-12 20:00:00');
insert into chat_message (user_id, chat_id, contents, written_time) values (3, 9, '권현아 채팅!!!', '2018-02-12 20:00:00');
insert into chat_message (user_id, chat_id, contents, written_time) values (4, 9, '송민석 채팅!!!', '2018-02-12 20:00:00');
insert into chat_message (user_id, chat_id, contents, written_time) values (5, 9, '박주하 채팅!!!', '2018-02-12 20:00:00');