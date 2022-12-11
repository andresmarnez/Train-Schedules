DROP DATABASE IF EXISTS trains;

CREATE DATABASE IF NOT EXISTS trains;
USE trains;

CREATE TABLE IF NOT EXISTS stations(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) UNIQUE NOT NULL,
    city VARCHAR(32) NOT NULL,
    coordinates VARCHAR(128)
);

CREATE TABLE IF NOT EXISTS line(
	id INT PRIMARY KEY AUTO_INCREMENT,
	origin_station INT,
    end_station INT,
    UNIQUE KEY(origin_station, end_station),
    CONSTRAINT fk_origin FOREIGN KEY connections(origin_station) REFERENCES stations(id),
    CONSTRAINT fk_end FOREIGN KEY connections(end_station) REFERENCES stations(id)
);

CREATE TABLE IF NOT EXISTS trains(
	id INT PRIMARY KEY AUTO_INCREMENT,
	num_wagons INT,
    checked_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    retired_time TIMESTAMP DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS connections(
	id INT PRIMARY KEY AUTO_INCREMENT,
	id_line INT NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
	CONSTRAINT fk_line FOREIGN KEY connections(id_line) REFERENCES line(id)
);

CREATE TABLE IF NOT EXISTS trains_route(
	id_train INT PRIMARY KEY ,
    id_connection INT,
	CONSTRAINT fk_train FOREIGN KEY trains_route(id_train) REFERENCES trains(id),
    CONSTRAINT fk_connection FOREIGN KEY trains_route(id_connection) REFERENCES connections(id_line)
);

INSERT stations(id,name,city) VALUES
(1,"Atocha","Madrid"),
(2,"Albacete-Los Llanos","Albacete"),
(3,"Zaragoza Delicias","Zaragoza"),
(4,"Santa Justa","Sevilla"),
(5,"Sants","Barcelona"),
(6,"Elche AVE","Elche"),
(7,"Joaquín Sorolla","Valencia"),
(8,"Alicante Terminal","Alicante"),
(9,"María Zambrano","Málaga"),
(10,"Murcia del Carmen","Murcia");

INSERT line(origin_station,end_station) VALUES 
(1,2), (1,3), (1,4), (1,5), (1,7), (1,9), (1,10),
(2,1), (2,6), (2,8), (2,10), 
(3,1), (3,5),
(4,1), (4,9), 
(5,1), (5,3),
(6,2), (6,8), (6,10),
(7,1),
(8,2), (8,6),
(9,1), (9,4),
(10,1), (10,2), (10,6);

INSERT connections(id_line,departure_time,arrival_time) VALUES
(1,'08:45','10:15'), (1,'18:45','20:15'),	-- MAD   /   ALB
(2,'10:15','11:35'), (2,'15:45','17:15'),	--       /   ZGZ
(3,'9:45','13:35'), (3,'19:15','23:00'),	--       /   SEV
(4,'8:45','12:35'), (4,'16:15','20:05'),	--       /   BCN
(5,'14:15','17:35'), 						--       /   VLC
(6,'7:45','10:35'), (6,'17:45','20:35'),	--       /   MLG
(7,'11:10','13:55'),						--       /   MUR
(8,'10:15','11:35'), (8,'15:45','16:15'),	-- ALB   /   MAD
(9,'10:15','11:55'), (9,'20:15','21:30'),	--       /   ELX
(10,'10:15','11:25'), (10,'20:15','21:00'),	--       /   ALC
(11,'12:25','13:15'), (11,'17:15','18:55'),	--       /   MUR
(12,'11:50','13:20'), (12,'17:30','19:00'),	-- ZGZ   /   MAD
(13,'11:40','12:45'), (13,'17:20','18:25'),	--       /   BCN
(14,'14:05','12:45'), (14,'20:45','22:25'),	-- SEV   /   MAD
(15,'10:10','11:05'),						--       /   MLG
(16,'12:40','16:05'), (16,'20:15','23:40'),	-- BCN   /   MAD
(17,'12:50','13:55'), (17,'22:45','23:50'),	--       /   ZGZ
(18,'8:05','9:20'), (18,'20:45','22:00'),	-- ELX   /   ALB
(19,'6:45','7:25'), (19,'9:25','10:15'), (19,'14:15','14:55'), (19,'18:00','18:40'),	--       /   ALC
(20,'7:45','10:35'), (20,'17:45','20:35'),	
(21,'11:10','13:55'),						
(22,'10:15','11:35'), (22,'15:45','16:15'),
(23,'10:15','11:55'), (23,'20:15','21:30'),	
(24,'10:15','11:25'), (24,'20:15','21:00'),
(25,'12:25','13:15'), (25,'17:15','18:55'),	
(26,'11:50','13:20'), (26,'17:30','19:00'),	
(27,'11:40','12:45'), (27,'17:20','18:25'),	
(28,'8:05','9:45'), (28,'20:45','22:25');	

INSERT trains(id,num_wagons) VALUES 
(101, 13),
(666, 12),
(042, 10),
(321, 12),
(123, 12);

INSERT trains_route(id_train,id_connection) VALUES 
(101, 2),
(666, 10),
(042, 16),
(321, 23),
(123, 25);

