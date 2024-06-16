/*
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS covertypes;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS orderitems;
DROP TABLE IF EXISTS orderStatuses; 
*/


CREATE TABLE IF NOT EXISTS orderstatuses (
    id SERIAL PRIMARY KEY,
	name  VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO orderstatuses (name) VALUES ('ISSUED');
INSERT INTO orderstatuses (name) VALUES ('CONFIRMED');
INSERT INTO orderstatuses (name) VALUES ('PAID');
INSERT INTO orderstatuses (name) VALUES ('CANCELLED');
	
CREATE TABLE IF NOT EXISTS  orders (
    id SERIAL PRIMARY KEY,
	user_id INT8 REFERENCES  users(id) ON DELETE CASCADE ON UPDATE CASCADE,
	cost DECIMAL(24,2),
	status_id int2,
	deleted boolean NOT NULL
	
);

CREATE TABLE IF NOT EXISTS  orderitems (
    id SERIAL PRIMARY KEY,
	order_id INT8 REFERENCES  orders(id) ON DELETE CASCADE ON UPDATE CASCADE,
	quantity INT8,
	price DECIMAL(24,2),
	deleted boolean NOT NULL
);



CREATE TABLE IF NOT EXISTS  covertypes (
    id SERIAL PRIMARY KEY, 
	name  VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO covertypes (name) VALUES ('HARD');
INSERT INTO covertypes (name) VALUES ('SOFT');
INSERT INTO covertypes (name) VALUES ('HINGED');
INSERT INTO covertypes (name) VALUES ('SPRING');

CREATE TABLE IF NOT EXISTS books (
	id SERIAL PRIMARY KEY, 
 	author VARCHAR(100),
    isbn VARCHAR(50) NOT NULL, 
 	year INT4, 
 	cost DECIMAL(24,2),
	covertypes_id INT8 REFERENCES  covertypes(id) ON DELETE CASCADE ON UPDATE CASCADE,
	deleted boolean NOT NULL
);


INSERT INTO books(author, isbn, year, cost, covertypes_id, deleted) VALUES
	('a1','978-1-123456-47-2',2015,45.5,1,false),('a2','977-0-123456-47-2',2015,80,1,false),('a6','978-6-103456-47-2',2020,72,1,false), ('a6','978-6-120056-47-2',2023,56,1,false), 
  	('a3','978-2-123456-47-2',2005,33.3,2,false),('a4','978-3-123456-47-2',2017,80,2,false),('a5','979-0-123456-47-2',2018,13.5,2,false),('a6','978-6-123456-47-2',2003,12,1,false),
	('a7','978-0-144456-41-2',2013,45,2,false),('a8','978-1-123856-47-2',2012,54.2,2,false),('a9','978-4-123456-47-2',2010,75.1,1,false),('a10','978-8-123456-47-2',2016,25,1,false),
	('a11','978-0-123456-49-2',2018,21.03,3,false),('a12','978-0-123456-48-2',2018,80.7,3,false),('a13','978-0-123456-47-8',2019,null,1,false),('a14','988-0-123456-47-2',2020,13,1,false),
	('a15','778-0-123456-47-2',2020,10,1,false),('a16','978-2-123856-47-2',2020,45.2,1,false),('a17','978-0-123456-57-2',2018,null,1,false),('a18','978-0-723456-47-2',2014,65,1,false),
	('a19','978-0-124456-47-2',2012,45.12,2,false),('a20','978-0-123488-47-2',2013,56.5,2,false),('a21','978-0-129886-47-2',2023,46.2,1,false);



CREATE TABLE IF NOT EXISTS  roles (
    id SERIAL PRIMARY KEY, 
	name  VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('MANAGER');
INSERT INTO roles (name) VALUES ('CUSTOMER');

CREATE TABLE IF NOT EXISTS  users (
    id SERIAL PRIMARY KEY, 
	login  VARCHAR(50) NOT NULL,
	password VARCHAR(50) NOT NULL,
	first_name VARCHAR(50),
	last_name VARCHAR(50), 
	email VARCHAR(50),
	roles_id INT8 REFERENCES  roles(id) ON DELETE CASCADE ON UPDATE CASCADE,
	deleted boolean NOT NULL
);

INSERT INTO users (login, password, first_name, last_name, email, roles_id, deleted ) VALUES
	('user1','978-1-123456-47-2','First1','Last1','email1@mail.ru',1,false), ('user2','977-0-123456-47-2','First2','Last2','email2@mail.ru',1,false), ('user3','978-6-103456-47-2','First3','Last3','email3@mail.ru',3,false),
  	('user5','978-2-123456-47-3','First5','Last5','email5@mail.ru',1,false), ('user10','977-0-144456-47-2','First10','Last10','email10@mail.ru',3,false), ('user21','979-0-123456-47-2','First21','Last21','email21@mail.ru',3,false),
	('user6','978-0-144456-41-3','First6','Last6','email6@mail.ru',3,false), ('user11','977-0-553456-47-2','First11','Last11','email11@mail.ru',2,false), ('user22','978-4-123456-47-2','First22','Last22','email22@mail.ru',3,false),
	('user7','978-0-123456-49-3','First7','Last7','email7@mail.ru',3,false), ('user12','977-0-663456-47-2','First12','Last12','email12@mail.ru',2,false), ('user23','978-0-123456-47-8','First23','Last23','email23@mail.ru',3,false),
	('user8','778-0-123456-47-3','First8','Last8','email8@mail.ru',3,false), ('user16','977-0-773456-47-2','First16','Last13','email16@mail.ru',3,false), ('user24','978-0-123456-57-2','First24','Last24','email24@mail.ru',3,false),
	('user9','978-0-124456-47-3','First9','Last9','email9@mail.ru',3,false), ('user20','977-0-883456-47-2','First14','Last14','email14@mail.ru',3,false), ('user25','978-0-129886-47-2','First25','Last25','email25@mail.ru',3,false);
