DROP DATABASE IF EXISTS trains;

CREATE DATABASE IF NOT EXISTS trains;
USE trains;

CREATE TABLE IF NOT EXISTS stations(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) UNIQUE NOT NULL,
    city VARCHAR(32) NOT NULL,
    coordenates VARCHAR(128)
);

CREATE TABLE IF NOT EXISTS `lines`(
	id INT UNIQUE AUTO_INCREMENT,
	origin INT,
    end INT,
    PRIMARY KEY(origin, end),
    CONSTRAINT fk_origin FOREIGN KEY connections(origin) REFERENCES stations(id),
    CONSTRAINT fk_end FOREIGN KEY connections(end) REFERENCES stations(id)
);

CREATE TABLE IF NOT EXISTS trains(
	id INT PRIMARY KEY AUTO_INCREMENT,
	num_vagones INT,
    last_check TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS connections(
	id_line INT auto_increment NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
	CONSTRAINT fk_line FOREIGN KEY connections(id_line) REFERENCES `lines`(id)
);

CREATE TABLE IF NOT EXISTS trains_route(
	id_train INT PRIMARY KEY,
    id_connection INT UNIQUE,
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

INSERT `lines`(origin,end) VALUES 
(1,2), (1,3), (1,4), (1,5), (1,6), (1,7), (1,8), (1,9), (1,10),
(2,1), (2,6), (2,8), (2,10), 
(3,1), (3,5),
(4,1), (4,9), 
(5,1), (5,3),
(6,1), (6,2), (6,8), (6,10),
(7,1),
(8,1), (8,2), (8,6),
(9,1), (9,4),
(10,1), (10,2), (10,6);

INSERT trains(id,num_vagones) VALUES 
(101, 12),
(666, 10),
(042, 11),
(123, 9);


