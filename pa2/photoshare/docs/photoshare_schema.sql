DROP TABLE Users;
DROP TABLE Friends; 
DROP TABLE Albums; 
DROP TABLE Pictures;
DROP TABLE Tags; 
DROP TABLE Comments; 

DROP SEQUENCE Users_user_id_seq;
DROP SEQUENCE Albums_album_id_seq; 
DROP SEQUENCE Pictures_picture_id_seq;
DROP SEQUENCE Comments_comment_id_seq; 


CREATE SEQUENCE Users_user_id_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE TABLE Users
(
	user_id int4 NOT NULL DEFAULT nextval('Users_user_id_seq'),
	email varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	role_name varchar(255) NOT NULL DEFAULT 'RegisteredUser',
	first_name varchar(255) NOT NULL, 
	last_name varchar(255), 
	hometown varchar(255), 
	location varchar(255), 
	title varchar(255),
	rating int NOT NULL DEFAULT 0, 
	CONSTRAINT users_pk PRIMARY KEY (user_id)
);


CREATE TABLE Friends
( 
	user_id int4 NOT NULL REFERENCES Users (user_id) ON DELETE CASCADE, 
	friend_id int4 NOT NULL REFERENCES Users (user_id) ON DELETE CASCADE, 
	PRIMARY KEY (user_id, friend_id)
); 


CREATE SEQUENCE Albums_album_id_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE TABLE Albums
(
	album_id int4 NOT NULL DEFAULT nextval('Albums_album_id_seq'),
	user_id int4 NOT NULL REFERENCES Users (user_id) ON DELETE CASCADE,
	album_name varchar(255) NOT NULL,
	date_created date NOT NULL, 
	CONSTRAINT albums_pk PRIMARY KEY (album_id)
); 


CREATE SEQUENCE Pictures_picture_id_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE TABLE Pictures
(
	picture_id int4 NOT NULL DEFAULT nextval('Pictures_picture_id_seq'),
	album_id int4 NOT NULL REFERENCES Albums (album_id) ON DELETE CASCADE,
	caption varchar(255) NOT NULL,
	imgdata bytea NOT NULL,
	size int4 NOT NULL,
	content_type varchar(255) NOT NULL,
	thumbdata bytea NOT NULL,
	likes int NOT NULL DEFAULT 0,
	CONSTRAINT pictures_pk PRIMARY KEY (picture_id)
); 


CREATE TABLE Tags 
(
	tag varchar(255) NOT NULL, 
	picture_id int4 NOT NULL REFERENCES Pictures (picture_id) ON DELETE CASCADE, 
	PRIMARY KEY (tag, picture_id)
); 


CREATE SEQUENCE Comments_comment_id_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE TABLE Comments
(
	comment_id int4 NOT NULL DEFAULT nextval('Comments_comment_id_seq'), 
	user_id int4 NOT NULL REFERENCES Users (user_id) ON DELETE CASCADE,
	picture_id int4 NOT NULL REFERENCES Pictures (picture_id) ON DELETE CASCADE, 
	comment varchar(255) NOT NULL, 
	date_commented date NOT NULL, 
	PRIMARY KEY (comment_id)
); 


INSERT INTO Users (email, password, first_name, last_name, hometown, location, title) 
VALUES ('tmf@bu.edu', 'TImf$$2014', 'Tim', 'Farrell', 'Philadelphia', 'Boston', 'Student at BU');
