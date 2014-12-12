-- Role
INSERT INTO SYS_ROLE(id,name) VALUES (1,'ROLE_ADMIN');
INSERT INTO SYS_ROLE(id,name) VALUES (2,'ROLE_CUSTOMER');
INSERT INTO SYS_ROLE(id,name) VALUES (3,'ROLE_PHOTOGRAPHER');
INSERT INTO SYS_ROLE(id,name) VALUES (4,'ROLE_BECOME_PHOTOGRAPHER');

-- User
-- Password: 123
INSERT INTO SYS_USER(ID,EMAIL,PASSWORD,FIRST_NAME,LAST_NAME,COMPANY, PHONE, HOMEPAGE, IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('1', 'vincent@test.de','$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG', 'Vincent', 'Test', 'EGCompany', '030555555', 'wwww.mypage.com', true,true);
-- Password: 123
INSERT INTO SYS_USER(ID,EMAIL,PASSWORD,FIRST_NAME,LAST_NAME,COMPANY,PHONE,HOMEPAGE,IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('2', 'peter@test.de','$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG', 'Peter', 'Test', 'EGCompany', '030555555', 'wwww.mypage.com', true,true);
-- Password: 123
INSERT INTO SYS_USER(ID,EMAIL,PASSWORD,FIRST_NAME,LAST_NAME,COMPANY, PHONE, HOMEPAGE, IBAN, SWIFT, IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('3', 'sergej@test.de', '$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG', 'Sergej', 'Test', 'EGCompany', '030555555', 'wwww.mypage.com', 'DE50344342342424', 'DEFGHKZ',  true, true);
-- Password: 123
INSERT INTO SYS_USER(ID,EMAIL,PASSWORD,FIRST_NAME,LAST_NAME,COMPANY, PHONE, HOMEPAGE, IBAN, SWIFT, IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('4', 'julian@test.de', '$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG', 'Julian', 'Test', 'EGCompany', '030555555', 'wwww.mypage.com', 'DE50344342342424', 'DEFGHKZ', true, true);
-- Password: 123
INSERT INTO SYS_USER(ID,EMAIL,PASSWORD,FIRST_NAME,LAST_NAME,COMPANY, PHONE, HOMEPAGE, IBAN, SWIFT, IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('5', 'daniil@test.de', '$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG', 'Daniil','Test', 'EGCompany', '030555555', 'wwww.mypage.com', 'DE50344342342424', 'DEFGHKZ', true, true);
-- Password: 123
INSERT INTO SYS_USER(ID,EMAIL,PASSWORD,FIRST_NAME,LAST_NAME,COMPANY, PHONE, HOMEPAGE,  IBAN, SWIFT, IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('6', 'eric@test.de', '$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG', 'Eric', 'Test', 'EGCompany', '030555555', 'wwww.mypage.com', 'DE50344342342424', 'DEFGHKZ', true, true);
  -- Password: 123
INSERT INTO SYS_USER(ID,EMAIL,PASSWORD,FIRST_NAME,LAST_NAME,COMPANY, PHONE, HOMEPAGE,  IBAN, SWIFT, IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('7', 'become@photographer.de', '$2a$10$za9rDqJOMBXCWXEG5O4.U.1GqyXoiS0mjuiHwqhCvO9wRU6Z/NdGG', 'Become', 'Photographer', 'EGCompany', '030555555', 'wwww.mypage.com', 'DE50344342342424', 'DEFGHKZ', true, true);


-- SYS_USER_ROLE
--Vincent - Admin
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (1,1,1);
--Peter - Customer
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (2,2,2);
--Sergej - Photographer
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (3,3,3);
--Julian - Photographer
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (4,4,3);
--Daniil - Photographer
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (5,5,3);
--Eric - Photographer
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (6,6,3);
--BecomePhotographer - Become Photographer
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (7,7,4);

-- RES_IMAGE
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('1','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/1');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('2','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/2');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('3','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/3');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('4','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/4');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('5','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/5');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('6','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/6');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('7','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/7');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('8','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/8');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('9','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/9');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('10','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/10');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('11','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/11');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('12','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/12');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('13','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/13');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('14','ErdeUndMond', 'Planet,Erde,Mond,Universum','true','true','2.30','jpg','/store/14');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('15','Erde', 'Planet,Erde,Universum','true','true','2.30','jpg','/store/15');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('16','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/16');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('17','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/17');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('18','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/18');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('19','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/19');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('20','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/20');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('21','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/21');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('22','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/22');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('23','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/23');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('24','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/24');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('25','Milchstraße', 'Universum,Galaktik','true','true','2.30','jpg','/store/25');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('26','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/26');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('27','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/27');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('28','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/28');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('29','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/29');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('30','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/30');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('31','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/31');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('32','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/32');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('33','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/33');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('34','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/34');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('35','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/35');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('36','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/36');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('37','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/37');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('38','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/38');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('39','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/39');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('40','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/40');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('41','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/41');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('42','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/42');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('43','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/43');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('44','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/44');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('45','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/45');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('46','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/46');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('47','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/47');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('48','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/48');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('49','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/49');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('50','Steinberg', 'Gebirge,Natur','true','true','2.30','jpg','/store/50');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('51','GebirgeInNebel', 'Gebirge,Natur,Nebel','true','true','2.30','jpg','/store/51');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('52','GebirgeSonnenuntergang', 'Gebirge,Natur,Sonnenuntergang','true','true','2.30','jpg','/store/52');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('53','BergMitSee', 'Gebirge,Natur,See','true','true','2.30','jpg','/store/53');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('54','SteinbergMitSonnenuntergang', 'ebirge,Natur,Sonnenuntergang','true','true','2.30','jpg','/store/54');
INSERT INTO RES_IMAGE (ID,NAME,DESCRIPTION,IS_PUBLIC,IS_ENABLED,PRICE,MIME,PATH) VALUES ('55','Lorem ipsum', 'Lorem ipsum dolor sit amet.','true','true','2.30','jpg','/store/55');


-- image collections created by photographer.
INSERT INTO RES_COLLECTION (ID,USER_ID,NAME,DESCRIPTION) VALUES ('1','6', 'Natur pur!','Lorem ipsum dolor sit amet.');
INSERT INTO RES_COLLECTION (ID,USER_ID,NAME,DESCRIPTION) VALUES ('2','5', 'Irgendwas!','Lorem ipsum dolor sit amet.');
INSERT INTO RES_COLLECTION (ID,USER_ID,NAME,DESCRIPTION) VALUES ('3','4', 'Mal was Anderes','Lorem ipsum dolor sit amet.');
INSERT INTO RES_COLLECTION (ID,USER_ID,NAME,DESCRIPTION) VALUES ('4','5', 'Schon wieder eine Sammlung','Lorem ipsum dolor sit amet.');
INSERT INTO RES_COLLECTION (ID,USER_ID,NAME,DESCRIPTION) VALUES ('5','6', 'Paar Bilder','Lorem ipsum dolor sit amet.');
INSERT INTO RES_COLLECTION (ID,USER_ID,NAME,DESCRIPTION) VALUES ('6','3', 'Gebierge','Gebirge');
INSERT INTO RES_COLLECTION (ID,USER_ID,NAME,DESCRIPTION) VALUES ('7','3', 'Universum','Universum,Planet,Erde');

-- images uploaded by photographer. None of them have been bought yet
INSERT INTO RES_USER_IMAGE (ID,OWNER_ID,USER_ID,IMAGE_ID) VALUES ('1','3', '3','14');
INSERT INTO RES_USER_IMAGE (ID,OWNER_ID,USER_ID,IMAGE_ID) VALUES ('2','3', '3','15');
INSERT INTO RES_USER_IMAGE (ID,OWNER_ID,USER_ID,IMAGE_ID) VALUES ('3','3', '3','25');
INSERT INTO RES_USER_IMAGE (ID,OWNER_ID,USER_ID,IMAGE_ID) VALUES ('4','3', '3','50');
INSERT INTO RES_USER_IMAGE (ID,OWNER_ID,USER_ID,IMAGE_ID) VALUES ('5','3', '3','51');
INSERT INTO RES_USER_IMAGE (ID,OWNER_ID,USER_ID,IMAGE_ID) VALUES ('6','3', '3','52');
INSERT INTO RES_USER_IMAGE (ID,OWNER_ID,USER_ID,IMAGE_ID) VALUES ('7','3', '3','53');
INSERT INTO RES_USER_IMAGE (ID,OWNER_ID,USER_ID,IMAGE_ID) VALUES ('8','3', '3','54');

INSERT INTO RES_COLLECTION_IMAGE (ID,COLLECTION_ID,IMAGE_ID) VALUES ('1','6', '50');
INSERT INTO RES_COLLECTION_IMAGE (ID,COLLECTION_ID,IMAGE_ID) VALUES ('2','6', '51');
INSERT INTO RES_COLLECTION_IMAGE (ID,COLLECTION_ID,IMAGE_ID) VALUES ('3','6', '52');
INSERT INTO RES_COLLECTION_IMAGE (ID,COLLECTION_ID,IMAGE_ID) VALUES ('4','6', '53');
INSERT INTO RES_COLLECTION_IMAGE (ID,COLLECTION_ID,IMAGE_ID) VALUES ('5','6', '54');
INSERT INTO RES_COLLECTION_IMAGE (ID,COLLECTION_ID,IMAGE_ID) VALUES ('6','7', '15');