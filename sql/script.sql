CREATE SCHEMA VEM_SER;
--SET SCHEMA VEM_SER;
--


-- -----------------------------------------------------
-- Table BANK_ACCOUNT
-- -----------------------------------------------------
CREATE TABLE  BANK_ACCOUNT (
  id_bank_account numeric NOT NULL,
  account_number TEXT NOT NULL UNIQUE,
  agency TEXT NOT NULL,
  
  PRIMARY KEY (id_bank_account)
);
	CREATE SEQUENCE bank_account_seq
	 START    1
	 INCREMENT   1;

-- -----------------------------------------------------
-- Table USER
-- -----------------------------------------------------
CREATE TABLE  USERS (
  id_user numeric NOT NULL UNIQUE,
  name TEXT NOT NULL,
  email TEXT NOT NULL UNIQUE,
  password TEXT NOT NULL,
  type boolean NOT NULL,
  document TEXT NOT NULL UNIQUE,
  
  PRIMARY KEY (id_user)
);
	CREATE SEQUENCE users_seq
	 START    1
	 INCREMENT   1;
		
-- -----------------------------------------------------
-- Table REQUEST
-- -----------------------------------------------------
CREATE TABLE  REQUEST (
  id_request numeric NOT NULL,
  title TEXT NOT NULL,
  request_description TEXT NOT NULL,
  goal numeric(9, 2) NOT NULL,
  reached_value numeric(9, 2) NOT NULL,
  status_request boolean NOT NULL,
  id_category numeric NOT NULL,
  id_bank_account numeric NOT NULL,
  id_user numeric NOT NULL,
  
  PRIMARY KEY (id_request),
  CONSTRAINT fk_Request_BankAccount1
    FOREIGN KEY (id_bank_account)
    REFERENCES BANK_ACCOUNT (id_bank_account),
  CONSTRAINT fk_REQUEST_USER1
    FOREIGN KEY (id_user)
    REFERENCES USERS (id_user)
);
	CREATE SEQUENCE request_seq
	 START     1
	 INCREMENT   1;	

-- -----------------------------------------------------
-- Table DONATE
-- -----------------------------------------------------
CREATE TABLE  DONATE (
  id_donate numeric NOT NULL,
  id_request numeric NOT NULL,
  donator_name TEXT NOT NULL,
  donator_email TEXT NOT NULL,
  donate_value numeric(9, 2) NOT NULL,
  donate_description TEXT,
  
  PRIMARY KEY (id_donate),
  CONSTRAINT fk_Request
    FOREIGN KEY (id_request)
    REFERENCES REQUEST (id_request)

);
	CREATE SEQUENCE donate_seq
	 START     1
	 INCREMENT   1;

-- -----------------------------------------------------
-- Table ROLES
-- -----------------------------------------------------
CREATE TABLE ROLES (
    ID_ROLE NUMERIC NOT NULL,
    NAME TEXT UNIQUE NOT NULL,

    PRIMARY KEY(ID_ROLE)
);

CREATE sequence ROLES_SEQ
 START     1
 INCREMENT   1;

-- -----------------------------------------------------
-- Table USER_ROLE
-- -----------------------------------------------------
CREATE TABLE USER_ROLE (
    ID_USER NUMERIC NOT NULL,
    ID_ROLE NUMERIC NOT NULL,
    PRIMARY KEY(ID_USER, ID_ROLE),
    CONSTRAINT FK_USERS_USER_ROLE FOREIGN KEY (ID_USER) REFERENCES USERS (ID_USER),
  	CONSTRAINT FK_ROLES_USER_ROLE FOREIGN KEY (ID_ROLE) REFERENCES ROLES (ID_ROLE)
);





-- -----------------------------------------------------
-- INSERT BANK_ACCOUNT
-- -----------------------------------------------------
INSERT INTO BANK_ACCOUNT (id_bank_account, account_number, agency)
VALUES (nextval('bank_account_seq'), '2202512', '1005');
INSERT INTO BANK_ACCOUNT (id_bank_account, account_number, agency)
VALUES (nextval('bank_account_seq'), '1010256', '1008');
INSERT INTO BANK_ACCOUNT (id_bank_account, account_number, agency)
VALUES (nextval('bank_account_seq'), '7020855', '1205');
INSERT INTO BANK_ACCOUNT (id_bank_account, account_number, agency)
VALUES (nextval('bank_account_seq'), '210252', '1005');
INSERT INTO BANK_ACCOUNT (id_bank_account, account_number, agency)
VALUES (nextval('bank_account_seq'), '1810256', '1008');
INSERT INTO BANK_ACCOUNT (id_bank_account, account_number, agency)
VALUES (nextval('bank_account_seq'), '7900255', '1205');

-- -----------------------------------------------------
-- INSERT USER
-- -----------------------------------------------------
INSERT  INTO USERS (id_user, name, email, password, type, document)
VALUES (nextval('users_seq'), 'admin', 'admin', '$2a$12$8ip8ptNhDTAVqH6ePel51O8no1b2MvQZd5PsB.zZhVBWceT61ghu.', false, '73775006036');
INSERT  INTO USERS (id_user, name, email, password, type, document)
VALUES (nextval('users_seq'), 'Liane', 'liane@gmail.com', '$2a$12$GL8U7Mzas9wie2jntRuuuOAUTOVTquZFng6AzuhStaWm0hCJ455oO', false, '23156682047');
INSERT  INTO USERS (id_user, name, email, password, type, document)
VALUES (nextval('users_seq'), 'Claudia', 'claudia@gmail.com', '$2a$12$GL8U7Mzas9wie2jntRuuuOAUTOVTquZFng6AzuhStaWm0hCJ455oO', false, '22157284001');
INSERT  INTO USERS (id_user, name, email, password, type, document)
VALUES (nextval('users_seq'), 'Rodrigo', 'rodrigo@gmail.com', '$2a$12$GL8U7Mzas9wie2jntRuuuOAUTOVTquZFng6AzuhStaWm0hCJ455oO', true, '82112413000122');

-- -----------------------------------------------------
-- INSERT ROLES
-- -----------------------------------------------------
insert into roles (id_role, name) 
values (nextval('roles_seq'), 'ROLE_ADMIN');
insert into roles (id_role, name) 
values (nextval('roles_seq'), 'ROLE_USER');

-- -----------------------------------------------------
-- INSERT USER_ROLE
-- -----------------------------------------------------
insert into user_role (id_user, id_role)
values (1,1);
insert into user_role (id_user, id_role)
values (2,2);
insert into user_role (id_user, id_role)
values (3,2);
insert into user_role (id_user, id_role)
values (4,2);


-- -----------------------------------------------------
-- INSERT REQUEST
-- -----------------------------------------------------
INSERT INTO REQUEST (id_request, title, request_description, goal, reached_value, status_request, id_category, id_bank_account, id_user)
VALUES (nextval('request_seq'), 'Crianca faminta', ' Nao temos dinheiro para alimentar nossa filha de 3 anos', 50000, 0, true, 2, 1, 2);
INSERT INTO REQUEST (id_request, title, request_description, goal, reached_value, status_request, id_category, id_bank_account, id_user)
VALUES (nextval('request_seq'), 'Meu pai esta morrendo', ' Nao temos dinheiro para os remedios', 100000, 0, true, 3, 2, 2);
INSERT INTO REQUEST (id_request, title, request_description, goal, reached_value, status_request, id_category, id_bank_account, id_user)
VALUES (nextval('request_seq'), 'Anne nao tem fimilia, pais e comida', ' Uma refugiada Ucraniana de 7 anos, precisamos de dinheiro pra manter a ong', 200000, 0, true, 6, 3, 3);

-- -----------------------------------------------------
-- INSERT DONATE
-- -----------------------------------------------------
INSERT INTO DONATE (id_donate, id_request , donator_name, donator_email, donate_value, donate_description)
VALUES (nextval('donate_seq'), 1, 'Ana', 'ana@gmail.com', 500.0, 'Boa sorte');
INSERT INTO DONATE (id_donate, id_request ,donator_name, donator_email, donate_value, donate_description)
VALUES (nextval('donate_seq'), 3, 'Nicolas', 'nicolas@gmail.com', 100.0, 'melhoras');
INSERT INTO DONATE (id_donate, id_request , donator_name, donator_email, donate_value, donate_description)
VALUES (nextval('donate_seq'), 2, 'Lucas', 'lucas@gmail.com', 100.0, NULL);