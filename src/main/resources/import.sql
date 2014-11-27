-- Role
INSERT INTO SYS_ROLE(id,name) VALUES (1,'ROLE_ADMIN');
INSERT INTO SYS_ROLE(id,name) VALUES (2,'ROLE_CUSTOMER');
INSERT INTO SYS_ROLE(id,name) VALUES (3,'ROLE_PHOTOGRAPHER');

-- User
-- Password: 123
INSERT INTO SYS_USER(ID,USERNAME,PASSWORD,EMAIL,COMPANY, PHONE, HOMEPAGE, IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('1', 'Vincent','$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG','vincent@test.de', 'EGCompany', '030555555', 'wwww.mypage.com', true,true);
-- Password: 123
INSERT INTO SYS_USER(ID,USERNAME,PASSWORD,EMAIL,COMPANY, PHONE, HOMEPAGE, IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('2', 'Peter','$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG','peter@test.de','EGCompany', '030555555', 'wwww.mypage.com', true,true);
-- Password: 123
INSERT INTO SYS_USER(ID,USERNAME,PASSWORD,EMAIL,COMPANY, PHONE, HOMEPAGE, IBAN, SWIFT, IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('3', 'Sergej','$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG','sergej@test.de','EGCompany', '030555555', 'wwww.mypage.com', 'DE50344342342424', 'DEFGHKZ',  true,true);
-- Password: 123
INSERT INTO SYS_USER(ID,USERNAME,PASSWORD,EMAIL,COMPANY, PHONE, HOMEPAGE, IBAN, SWIFT, IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('4', 'Julian','$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG','julian@test.de', 'EGCompany', '030555555', 'wwww.mypage.com', 'DE50344342342424', 'DEFGHKZ', false,true);
-- Password: 123
INSERT INTO SYS_USER(ID,USERNAME,PASSWORD,EMAIL,COMPANY, PHONE, HOMEPAGE, IBAN, SWIFT, IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('5', 'Danil','$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG','danil@test.de','EGCompany', '030555555', 'wwww.mypage.com', 'DE50344342342424', 'DEFGHKZ', false,true);
-- Password: 123
INSERT INTO SYS_USER(ID,USERNAME,PASSWORD,EMAIL, COMPANY, PHONE, HOMEPAGE, IBAN, SWIFT, IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('6', 'Eric','$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG','eric@test.de','EGCompany', '030555555', 'wwww.mypage.com', 'DE50344342342424', 'DEFGHKZ', false,true);


-- SYS_USER_ROLE
--Vincent - Admin
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (1,1,1);
--Peter - Customer
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (2,2,2);
--Sergej - Photographer
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (3,3,3);
--Julian - Photographer
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (4,4,3);
--Danil - Photographer
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (5,5,3);
--Eric - Photographer
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (6,6,3);

-- RES_IMAGE
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('1','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/1');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('2','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/2');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('3','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/3');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('4','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/4');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('5','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/5');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('6','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/6');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('7','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/7');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('8','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/8');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('9','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/9');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('10','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/10');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('11','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/11');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('12','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/12');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('13','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/13');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('14','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/14');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('15','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/15');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('16','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/16');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('17','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/17');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('18','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/18');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('19','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/19');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('20','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/20');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('21','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/21');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('22','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/22');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('23','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/23');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('24','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/24');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('25','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/25');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('26','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/26');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('27','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/27');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('28','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/28');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('29','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/29');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('30','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/30');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('31','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/31');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('32','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/32');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('33','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/33');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('34','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/34');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('35','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/35');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('36','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/36');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('37','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/37');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('38','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/38');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('39','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/39');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('40','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/40');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('41','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/41');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('42','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/42');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('43','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/43');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('44','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/44');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('45','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/45');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('46','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/46');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('47','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/47');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('48','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/48');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('49','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/49');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('50','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/50');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('51','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/51');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('52','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/52');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('53','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/53');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('54','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/54');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,COMRESSION,PATH) VALUES ('55','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/55');

-- images uploaded by photographer. None of them have been bought yet
INSERT INTO RES_COLLECTION (ID,USER_ID,NAME,DESCRIPTION) VALUES ('1','6', 'Natur pur!','Lorem ipsum dolor sit amet.');
INSERT INTO RES_COLLECTION (ID,USER_ID,NAME,DESCRIPTION) VALUES ('2','5', 'Irgendwas!','Lorem ipsum dolor sit amet.');
INSERT INTO RES_COLLECTION (ID,USER_ID,NAME,DESCRIPTION) VALUES ('3','4', 'Mal was Anderes','Lorem ipsum dolor sit amet.');
INSERT INTO RES_COLLECTION (ID,USER_ID,NAME,DESCRIPTION) VALUES ('4','5', 'Schon wieder ne Sammlung','Lorem ipsum dolor sit amet.');
INSERT INTO RES_COLLECTION (ID,USER_ID,NAME,DESCRIPTION) VALUES ('5','6', 'Paar Bilder','Lorem ipsum dolor sit amet.');
