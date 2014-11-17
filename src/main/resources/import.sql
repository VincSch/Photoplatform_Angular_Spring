-- Role
INSERT INTO SYS_ROLE(id,name) VALUES (1,'ADMIN');
INSERT INTO SYS_ROLE(id,name) VALUES (2,'CUSTOMER');
INSERT INTO SYS_ROLE(id,name) VALUES (3,'PHOTOGRAPHER');

-- User
-- Password: 123
INSERT INTO SYS_USER(ID,USERNAME,PASSWORD,EMAIL,IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('1', 'Vincent','$2a$10$hCv1HCi87rY8eRqir40wweQldxK7m7k2G/NQmlTteY6Felmtunyri','vincent@test.de',true,true);
-- Password: 123
INSERT INTO SYS_USER(ID,USERNAME,PASSWORD,EMAIL,IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('2', 'Peter','$2a$10$DhKmIWZdd/Wfc1fQJX.R..JdfqX/eFiCLwrKqca.ogN4wvuoYMaNK','peter@test.de',true,true);
-- Password: 123
INSERT INTO SYS_USER(ID,USERNAME,PASSWORD,EMAIL,IS_ENABLED,IS_ACCOUNT_NON_LOCKED) VALUES('3', 'Sergej','$2a$10$OFngnw1IWB4W2tFKjs.LueFEMXGRAQYgdjVqHvT2tD6qSIRGQpora','sergej@test.de',true,true);


-- SYS_USER_ROLE
--Vincent - Admin
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (1,1,1);
--Patrick - Customer
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (2,2,2);
--Sergej - Photographer
INSERT INTO SYS_USER_ROLE (id,user_id,role_id) VALUES (3,3,3);


-- SYS_USERPROFILE
INSERT INTO SYS_USERPROFILE (id,user_id, firstname, surname, address, BIRTHDAY,company,homepage,phone) VALUES('1','3','Sergej','Meister','Berlinerstraße 12,12207 Berlin','15.11.14','Burg',null,'016568334');

-- SYS_USERBANK
INSERT INTO SYS_USERBANK (ID,USER_ID,RECEIVER,IBAN,BIC) VALUES ('1','3', 'Sergej Meister','DE50344342342424','DEFGHKZ');
