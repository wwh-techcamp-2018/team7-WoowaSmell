insert into user (user_id, password, name, phone_number) values ('serverwizard@naver.com', '$2a$10$xfi04lvpOR5J2H2aTINQ2e08V/n9Xjc9XWTDUz2Ry03zDbk4y/a4a', '자바지기', '012-1233-1233');
insert into user (user_id, password, name, phone_number) values ('serverwizard2@naver.com', '$2a$10$xfi04lvpOR5J2H2aTINQ2e08V/n9Xjc9XWTDUz2Ry03zDbk4y/a4a', '자바지기2', '013-1233-1233');

insert into restaurant (address, name) values ('잠실 송파점', '굽네치킨');
insert into restaurant (address, name) values ('잠실 방이점', '네네치킨');
insert into restaurant (address, name) values ('잠실 정육점', '포비피자');
insert into restaurant (address, name) values ('잠실 점점점', '도미노피자');
insert into restaurant (address, name) values ('부천점', '개미와배짱이');
insert into restaurant (address, name) values ('주하점', '맥도날드');
insert into restaurant (address, name) values ('현아점', '롯데리아');
insert into restaurant (address, name) values ('종완점', '버거킹');
insert into restaurant (address, name) values ('삼성생명', '놀부보쌈');
insert into restaurant (address, name) values ('잠실롯데타워', '맘스터치');

insert into food (name, restaurant_id) values ('싸이버거', 10);
insert into food (name, restaurant_id) values ('후라이드', 1);
insert into food (name, restaurant_id) values ('양념치킨', 2);
insert into food (name, restaurant_id) values ('간장치킨', 2);
insert into food (name, restaurant_id) values ('포비포테이토피자', 3);
insert into food (name, restaurant_id) values ('볼케이노치킨', 1);
insert into food (name, restaurant_id) values ('치즈버거', 10);

insert into order_food (food_id, quantity, ordered_user_id, order_time) values (1, 10, 1, '2018-05-02 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (2, 10, 1, '2018-05-02 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (3, 10, 1, '2018-05-02 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (4, 10, 1, '2018-05-02 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (2, 20, 2, '2018-05-02 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (3, 20, 2, '2018-05-02 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (1, 20, 2, '2018-05-02 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (4, 20, 2, '2018-05-02 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (4, 20, 2, '2018-05-02 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (4, 20, 2, '2018-05-02 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (4, 20, 2, '2018-05-02 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (4, 20, 2, '2018-05-02 20:00:00');
insert into order_food (food_id, quantity, ordered_user_id, order_time) values (4, 20, 2, '2018-05-02 20:00:00');


insert into review (title, contents, star_point, written_time, updated_time, order_food_id) values ('교촌치킨', '허니콤보 맛잇어요', 4, '2018-05-02 20:00:00', '2018-05-02 20:00:00', 1);
insert into review (title, contents, star_point, written_time, updated_time, order_food_id) values ('네네치킨', '맛나요', 4, '2018-06-02 20:00:00', '2018-05-02 20:00:00', 2);
insert into review (title, contents, star_point, written_time, updated_time, order_food_id) values ('석촌치킨', '존맛탱', 5, '2018-05-02 20:00:00', '2018-05-02 20:00:00', 3);
insert into review (title, contents, star_point, written_time, updated_time, order_food_id) values ('방이치킨', '입니다', 4, '2018-08-02 20:00:00', '2018-05-02 20:00:00', 4);
insert into review (title, contents, star_point, written_time, updated_time, order_food_id) values ('잠실치킨', '별로에요', 2.5, '2018-05-02 20:00:00', '2018-05-02 20:00:00', 5);
insert into review (title, contents, star_point, written_time, updated_time, order_food_id) values ('종완치킨', '노맛..', 1.5, '2018-07-02 20:00:00', '2018-05-02 20:00:00', 6);
insert into review (title, contents, star_point, written_time, updated_time, order_food_id) values ('맥날버거', '안먹어요', 1, '2018-05-02 20:00:00', '2018-05-02 20:00:00', 7);
insert into review (title, contents, star_point, written_time, updated_time, order_food_id) values ('롯데버거', '짱이에요', 5, '2018-05-02 20:00:00', '2018-05-02 20:00:00', 8);
insert into review (title, contents, star_point, written_time, updated_time, order_food_id) values ('크롱버거', '괜찮아요', 4.5, '2018-01-02 20:00:00', '2018-05-02 20:00:00', 9);
insert into review (title, contents, star_point, written_time, updated_time, order_food_id) values ('포비버거', '먹을만해요', 3.5, '2018-05-02 20:00:00', '2018-05-02 20:00:00', 10);
insert into review (title, contents, star_point, written_time, updated_time, order_food_id) values ('주하버거', '또먹을래요', 4.5, '2018-02-02 20:00:00', '2018-05-02 20:00:00', 11);
insert into review (title, contents, star_point, written_time, updated_time, order_food_id) values ('교촌피자', '허니콤보 맛잇어요', 4, '2018-05-02 20:00:00', '2018-05-02 20:00:00', 12);
insert into review (title, contents, star_point, written_time, updated_time, order_food_id) values ('주하피자', '또먹을래요', 4.5, '2018-02-02 20:00:00', '2018-05-02 20:00:00', 13);

insert into good (user_id, review_id) values (1, 1);
insert into good (user_id, review_id) values (1, 2);
insert into good (user_id, review_id) values (2, 3);
insert into good (user_id, review_id) values (1, 4);
insert into good (user_id, review_id) values (2, 5);
insert into good (user_id, review_id) values (1, 5);
insert into good (user_id, review_id) values (1, 6);
insert into good (user_id, review_id) values (2, 7);
insert into good (user_id, review_id) values (1, 8);
insert into good (user_id, review_id) values (2, 9);
insert into good (user_id, review_id) values (1, 10);
insert into good (user_id, review_id) values (1, 11);