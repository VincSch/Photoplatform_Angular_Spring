-- Role
INSERT INTO SYS_ROLE(id,name) VALUES (1,'ROLE_ADMIN');
INSERT INTO SYS_ROLE(id,name) VALUES (2,'ROLE_CUSTOMER');
INSERT INTO SYS_ROLE(id,name) VALUES (3,'ROLE_PHOTOGRAPHER');

-- User
-- Password: 123
INSERT INTO SYS_USER(ID,USERNAME,PASSWORD,EMAIL,IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('1', 'Vincent','123','vincent@test.de',true,true);
-- Password: 123
INSERT INTO SYS_USER(ID,USERNAME,PASSWORD,EMAIL,IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('2', 'Peter','123','peter@test.de',true,true);
-- Password: 123
INSERT INTO SYS_USER(ID,USERNAME,PASSWORD,EMAIL,IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('3', 'Sergej','123','sergej@test.de',true,true);


-- SYS_USER_ROLE
--Vincent - Admin
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (1,1,1);
--Test for adding multiple roles to a user
--INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (4,1,2);
--Peter - Customer
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (2,2,2);
--Sergej - Photographer
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (3,3,3);


-- SYS_USERPROFILE
INSERT INTO SYS_USERPROFILE (id,user_id, firstname, surname, address, BIRTHDAY,company,homepage,phone) VALUES('1','3','Sergej','Meister','Berlinerstraße 12,12207 Berlin','15.11.14','Burg',null,'016568334');

-- SYS_USERBANK
INSERT INTO SYS_USERBANK (ID,USER_ID,RECEIVER,IBAN,BIC) VALUES ('1','3', 'Sergej Meister','DE50344342342424','DEFGHKZ');
