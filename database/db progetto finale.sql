DROP DATABASE IF EXISTS Shiru;

CREATE DATABASE Shiru;

DROP USER IF EXISTS 'shiruser'@'localhost';
CREATE USER 'shiruser'@'localhost' IDENTIFIED BY 'shiruser';
GRANT ALL ON Shiru.* TO 'shiruser'@'localhost';

USE Shiru;



DROP TABLE IF EXISTS casa_produttrice;
CREATE TABLE Casa_produttrice(
	ID_casa_produttrice int unsigned NOT NULL auto_increment,
    nome varchar(30) NOT NULL,
    telefono varchar(15) NOT NULL UNIQUE,
    email varchar(40) NOT NULL UNIQUE,
    
    constraint PK_casa_produttrice
		primary key(ID_casa_produttrice)
        
)ENGINE=InnoDB AUTO_INCREMENT = 10;


DROP TABLE IF EXISTS prodotto;
CREATE TABLE Prodotto(
	SKU int unsigned NOT NULL auto_increment,
    nome varchar(200) NOT NULL,
    peso float4 unsigned,
    prezzo float unsigned NOT NULL,
    quantità int unsigned NOT NULL,
    ID_casa_produttrice int unsigned,
    category enum("LIBRO", "GADGET"),
    
    constraint PK_Prodotto
		primary key(SKU),
        
	foreign key (ID_casa_produttrice) references Casa_produttrice(ID_casa_produttrice)
		ON DELETE SET NULL
        ON UPDATE CASCADE

)ENGINE=InnoDB AUTO_INCREMENT = 1000;

DROP TABLE IF EXISTS libro;
CREATE TABLE Libro(
	SKU int unsigned NOT NULL,
    ISBN varchar(14) UNIQUE NOT NULL,
    pagine int unsigned ,
    lingua varchar(30),
    
    constraint PK_libro
    primary key(SKU),
    
    foreign key (SKU) references prodotto(SKU) 
		ON DELETE CASCADE
        ON UPDATE CASCADE

)ENGINE= InnoDB;

DROP TABLE IF EXISTS gadgets;
CREATE TABLE Gadgets(
	SKU int unsigned NOT NULL,
    modello varchar(15),
    Marchio varchar(20) NOT NULL,
    
    constraint PK_gadgets
		primary key(SKU),
        
	foreign key (SKU) references prodotto(SKU)
		ON DELETE CASCADE
        ON UPDATE CASCADE
        

)ENGINE= InnoDB;


DROP TABLE IF EXISTS autori;
CREATE TABLE Autori(
	ID_autore int unsigned NOT NULL auto_increment,
    nome varchar(30) NOT NULL,
    cognome varchar(30) NOT NULL,
    nArte varchar(30),
    
    constraint PK_autore
		primary key(ID_autore)


)ENGINE=InnoDB AUTO_INCREMENT = 10;

DROP TABLE IF EXISTS user;
CREATE TABLE user(
	id int unsigned NOT NULL auto_increment,
	email varchar(40) NOT NULL UNIQUE,
    password varchar(600) NOT NULL,
    nome varchar(20) NOT NULL,
    cognome varchar(20) NOT NULL,
    telefono char(10) NOT NULL UNIQUE,
    role ENUM("USER", "ADMIN") NOT NULL default "user",
    
    
	constraint PK_user
		primary key(id)

)ENGINE= InnoDB auto_increment = 1000;

DROP TABLE IF EXISTS user_address;
CREATE TABLE user_address(
	id int unsigned NOT NULL auto_increment,
	user_id varchar(40) NOT NULL,
    via varchar(50) NOT NULL,
    provincia varchar(50) NOT NULL,
    città varchar(50) NOT NULL,
    CAP int NOT NULL,
    
    constraint PK_user_address
		primary key(id),
        
	foreign key(user_id) references user(email)
		ON UPDATE CASCADE 
        ON DELETE CASCADE
	
)ENGINE= InnoDB auto_increment= 100;


#Entità Ordine prende il nome di Order Details
DROP TABLE IF EXISTS order_details;
CREATE TABLE order_details(
	ID_ordine int  unsigned NOT NULL auto_increment,
    data_pagamento date NOT NULL,
    createdAt Timestamp, 
    modifiedAt Timestamp,
    ID_pagamento int UNSIGNED NOT NULL  UNIQUE,
    importo_pagamento float NOT NULL,
    userId int unsigned NOT NULL,

	constraint PK_Ordini
		primary key(ID_ordine),
        
	constraint FK_ordini_email
		foreign key(userId) references user(id)
			ON DELETE CASCADE
            ON UPDATE CASCADE
    
)ENGINE=InnoDB AUTO_INCREMENT = 10;


#Relazione Composto prende il nome seguente
DROP TABLE IF EXISTS order_items;
CREATE TABLE order_items(
	ID_ordine int unsigned NOT NULL,
    SKU int unsigned NOT NULL,
    quantità int,
    sold_at float,
    
    constraint PK_composto
		primary key(ID_ordine, SKU),
        
	constraint FK_composto_id_ordine
		foreign key(ID_ordine) REFERENCES order_details(ID_ordine)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
	
	constraint FK_composto_SKU
		foreign key(SKU) REFERENCES Prodotto(SKU)
        ON UPDATE CASCADE
        ON DELETE CASCADE


)ENGINE= InnoDB;

DROP TABLE IF EXISTS cart;
CREATE TABLE cart(

	cart_id int unsigned NOT NULL auto_increment,
    user_id int unsigned unique NOT NULL,
    total DOUBLE(16,2) unsigned default 0.00,
    created_at timestamp NOT NULL,
    modified_at timestamp NOT NULL,
    
    constraint PK_cart
		primary key(cart_id),
     
     constraint FK_cart_userId
		foreign key(user_id) REFERENCES user(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
	

)ENGINE= InnoDB AUTO_INCREMENT = 10;


DROP TABLE IF EXISTS cart_items;
CREATE TABLE cart_items(
    cart_id int unsigned NOT NULL,
    SKU int unsigned NOT NULL,
    quantity int unsigned NOT NULL DEFAULT 0,
    insert_at timestamp NOT NULL,
    modified_at timestamp NOT NULL,
    
    
    constraint PK_cartItems
		primary key(cart_id, SKU),
        
	constraint FK_cart_items_cartId
		foreign key(cart_id) references cart(cart_id)
		ON DELETE CASCADE
        ON UPDATE CASCADE,
        
	constraint FK_cart_items_itemSKU
		foreign key(SKU) references prodotto(SKU)
        ON DELETE CASCADE
        ON UPDATE CASCADE


)ENGINE = InnoDB AUTO_INCREMENT = 100;


DROP TABLE IF EXISTS cookieAuth;
CREATE TABLE cookieAuth(
	id int unsigned not null auto_increment,
	userId int unsigned not null,
    selector  varchar(100) unique not null,
    validator varchar(600) unique not null,
    
    constraint PK_cookieAuth
		primary key (id),
        
	constraint FK_cookieAuth_userId
		foreign key(userId) references user(id)

)ENGINE = InnoDB AUTO_INCREMENT = 100; 


DROP TABLE IF EXISTS realizza;
CREATE TABLE Realizza(
	ID_autore int unsigned NOT NULL,
	SKU int unsigned NOT NULL,
    
    constraint PK_realizza
		primary key(ID_autore, SKU),
	
    foreign key(SKU) references Libro(SKU)
		ON DELETE CASCADE
        ON UPDATE CASCADE,
    
    foreign key(ID_autore) references Autori(ID_autore)
		ON DELETE CASCADE
        ON UPDATE CASCADE
)ENGINE= InnoDB;


DROP TABLE IF EXISTS struttura;
CREATE TABLE Struttura(
	Materiale varchar(30) NOT NULL,
  
	constraint PK_Struttura
		primary key(Materiale)
)ENGINE= InnoDB;


DROP TABLE IF EXISTS costituito;
CREATE TABLE Costituito(
	SKU int unsigned NOT NULL,
    Materiale varchar(30) NOT NULL,
    
    constraint PK_Costituito
		primary key(SKU, Materiale),

    foreign key(SKU) references Gadgets(SKU)
		ON DELETE CASCADE
        ON UPDATE CASCADE,

    foreign key(Materiale) references Struttura(Materiale)
		ON UPDATE CASCADE
        ON DELETE CASCADE
)ENGINE= InnoDB;

DROP VIEW IF EXISTS LibriView;
CREATE VIEW LibriView AS
	select p.SKU as SKU,
		nome, peso, prezzo, quantità, ID_casa_produttrice as id_producer, l.ISBN, l.pagine, l.lingua
		from Prodotto p inner JOIN Libro l on p.SKU = l.SKU;
        
		
        
DROP VIEW IF EXISTS ordersView;
CREATE VIEW ordersView AS
	SELECT  order_details.*, order_items.quantità, order_items.SKU
		FROM order_details INNER JOIN order_items ON order_items.ID_ordine = order_details.ID_ordine;



/*data population*/
/*Casa Produttrice*/

	INSERT INTO casa_produttrice(nome, telefono, email)
	values
    
		("J-POP", "09213312", "jpop@gmail.com"),
		("Planet Manga", "1137264", "planetinfo@gmail.com"),
		("Star Comics", "02314352", "starcomics@gmail.com"),
		("Adelphi", "432214341", "Adelphi@gmail.com"),
		("Corriere della sera","434104503", "corsera@gmail.it"),
		("Mondadori", "078431312", "mondadori@info.it"),
		("Bollatti Boringhieri", "0812343132", "bollattibr@gmail.com"),
		("QuattroRuote", "012354341", "quattro@info.it"),
		("Editori GLF laterza", "74313145", "laterza@gmail.com"),
		("Feltrinelli", "17532431", "feltrinelli@supporto.it"),
		("RBA", "365213414", "rbacontacts@gmail.com"),
		("HAM Planet", "08124151", "salamella@bresaola.it");
    
/*Prodotti*/

INSERT INTO prodotto(nome, peso, prezzo, quantità, ID_casa_produttrice, category)
VALUES
	("Steins;Gate Collection", 90, 15, 537, 10, "Libro"),
    ("Buchi Bianchi dentro l'orizzonte", 76, 14, 217, 13,  "Libro"),
    ("Sette brevi lezioni di fisica", 36, 10, 189, 13,  "Libro"),
    ("L-ordine del tempo", 215, 14, 178, 13,  "Libro"),
    ("Sei pezzi meno facili", 217, 13, 248, 13,  "Libro"),
    ("Sei pezzi facili", 211, 13, 198, 13, "Libro"),
    ("Schumacher la leggenda di un uomo normale", 198, 9, 67, 15, "Libro"),
    ("Che cos'è la scienza la rivoluzione di Anassimandro", 127, 14, 87, 13, "Libro"),
    ("Relatività", 154, 15, 174, 16, "Libro"),
    ("Enzo Ferrari un eroe italiano", 157, 5.80, 76, 17, "Libro"),
    ("Ci sono luoghi al mondo dove più che le regole è importante la gentilezza", 305, 17.50, 143, 14, "Libro"),
    ("Massa", 314, 18.50, 96, 13, "Libro"),
    ("Bastava chiedere!", 255, 17, 102, 18, "Libro"),
	("Smartwatch X1", 0.2, 99.99, 50, 20, "Gadget"),
    ("Hoverboard Z2", 2.5, 299.99, 30, 21,"Gadget"),
    ("Neuralyzer 3000", 0.5, 49.99, 100, 17, "Gadget"),
    ("Lightsaber MKII", 0.3, 149.99, 20, 16, "Gadget"),
    ("Replicator 5000", 1.0, 499.99, 10, 12, "Gadget"),
    ("Sonic Screwdriver", 0.1, 29.99, 200, 11, "Gadget"),
    ('Il signore degli anelli', 1.5, 20.99, 50, 10, "Gadget"),
	('Harry Potter e la pietra filosofale', 1.2, 15.99, 70, 11, "Gadget"),
	('1984', 0.8, 12.99, 30, 12, "Libro"),
	('Il giovane Holden', 0.7, 10.99, 40, 13,  "Libro"),
	('Cime tempestose', 1.0, 18.99, 25, 14, "Libro"),
	('Don Chisciotte', 1.3, 22.99, 35, 15, "Libro"),
	('Il grande Gatsby', 0.9, 14.99, 60, 16, "Libro"),
	('Moby Dick', 1.8, 25.99, 20, 17, "Libro"),
	('Orgoglio e Pregiudizio', 1.1, 16.99, 45, 18, "Libro"),
	('Cronache di Narnia', 1.4, 19.99, 55, 19, "Libro"),
	('La ragazza con il drago tattoo', 1.2, 17.99, 38, 20, "Libro"),
	('Lo Hobbit', 1.0, 14.99, 48, 21, "Libro"),
	('Il nome della rosa', 1.5, 21.99, 42, 10, "Libro"),
	('Anna Karenina', 1.1, 16.99, 33, 11, "Libro"),
	("L'Assassinio di Roger Ackroyd", 0.8, 12.99, 28, 12, "Libro"),
	('Piccole Donne', 1.2, 18.99, 50, 13, "Libro"),
	('Il conte di Montecristo', 1.6, 23.99, 37, 14, "Libro"),
	('Il vecchio e il mare', 0.9, 15.99, 29, 15, "Libro"),
	('Le mille e una notte', 1.3, 20.99, 43, 16, "Libro"),
	('Cinquanta sfumature di grigio', 1.2, 17.99, 55, 17, "Libro"),
	('Il Piccolo Principe', 0.7, 10.99, 60, 18, "Libro"),
	('Guerra e pace', 1.8, 24.99, 25, 19, "Libro"),
	('Il codice Da Vinci', 1.4, 19.99, 32, 20, "Libro"),
	('Il giorno della civetta', 1.0, 14.99, 27, 21, "Libro"),
	('Il fu Mattia Pascal', 0.9, 16.99, 40, 10, "Libro"),
	('Il medico di corte', 1.1, 17.99, 34, 11, "Libro"),
	('La tregua', 1.5, 21.99, 22, 12, "Libro"),
	('Il barone rampante', 1.2, 18.99, 30, 13, "Libro"),
	('Il sole sorge ancora', 0.8, 13.99, 45, 14, "Libro"),
	("Cent'anni di solitudine", 1.3, 22.99, 28, 15, "Libro"),
	('Lo zoo di vetro', 1.0, 15.99, 36, 16, "Libro");

	INSERT INTO Libro(SKU, ISBN, pagine, lingua)
	values
		(1000, "8832757915741", 576, "italiano"),
		(1001, "9788845937538", 143, "italiano"),
        (1002, "9788806213949", 320, "italiano"),
		(1003, "9788866324574", 256, "italiano"),
		(1004, "9788806238713", 412, "italiano"),
		(1005, "9788838933015", 198, "italiano"),
		(1006, "9788804668275", 432, "italiano"),
		(1007, "9788804687320", 288, "italiano"),
		(1008, "9788804673507", 364, "italiano"),
		(1009, "9788804666882", 224, "italiano"),
		(1010, "9788854164523", 180, "italiano"),
		(1011, "9788804656760", 310, "italiano"),
        (1012, "9787704656760", 356, "italiano"),
		(1019, '1901238754567', 430, 'italiano'),
		(1020, '2012345676898', 360, 'italiano'),
		(1021, '1123456757989', 410, 'italiano'),
		(1022, '2234567867390', 380, 'italiano'),
		(1023, '3344567567901', 340, 'italiano'),
		(1024, '4456787899012', 470, 'italiano'),
		(1025, '5567878990123', 440, 'italiano'),
		(1026, '6678901626734', 310, 'italiano'),
		(1027, '7778989012345', 360, 'italiano'),
		(1028, '8900146723456', 390, 'italiano'),
		(1029, '9012348494567', 420, 'italiano'),
		(1030, '0123455745678', 370, 'italiano'),
		(1031, '1233463356789', 340, 'italiano'),
		(1032, '2344567897890', 430, 'italiano'),
		(1033, '3455677898901', 380, 'italiano'),
		(1034, '4566787899012', 340, 'italiano'),
		(1035, '5677878990123', 410, 'italiano'),
		(1036, '6787978901234', 360, 'italiano'),
		(1037, '7898078912345', 320, 'italiano'),
		(1038, '8900127893456', 370, 'italiano'),
		(1039, '9011237894567', 430, 'italiano'),
		(1040, '0123477895678', 390, 'italiano'),
		(1041, '1234566789901', 340, 'italiano'),
		(1042, '2345677899010', 450, 'italiano'),
		(1043, '3456788901901', 400, 'italiano'),
		(1044, '4567899012901', 360, 'italiano'),
		(1045, '5678900190123', 330, 'italiano'),
		(1046, '6789001239014', 380, 'italiano'),
		(1047, '7890901112345', 340, 'italiano'),
		(1048, '8909011123456', 420, 'italiano'),
		(1049, '9012341234567', 370, 'italiano');




	INSERT INTO user(email, password, nome, cognome, telefono)
	VALUES
		("franco31@gmail.com", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Francesco", "Sciarria", "3275216612"),
		("1001", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Mario", "Bianchi", "9876543210"),
		("1002", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Lucia", "Verdi", "4567890123"),
		("1003", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Giulia", "Russo", "7890123456"),
		("1004", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Andrea", "Ferrari", "2345648901"),
		("1005", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Alessia", "Romano", "5678901234"),
		("1006", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Davide", "Gallo", "0123456789"),
		("1007", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Roberta", "Conti", "9012345678"),
		("1008", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Luigi", "Marini", "3456789012"),
		("1009", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Sara", "Colombo", "6719012345"),
		("1010", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Marina", "Greco", "1234509876"),
		("1011", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Francesco", "Caruso", "0987612345"),
		("ilaria53@gmail.com", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Ilaria", "Leone", "5432109876"),
		("fabio64@gmail.com", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Fabio", "Barbieri", "8765432109"),
		("silvia75@gmail.com", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Silvia", "Santoro", "2345678901"),
		("angelo86@gmail.com", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Angelo", "Farina", "6789012345"),
		("giorgia97@gmail.com", "$argon2id$v=19$m=65536,t=44,p=2$matLhY5b5ebSQifd9epprzh6yeHBlrDGloU2WZaEugY=$5IHjwmDP3CfV+cHb0Fi45MxJUGjTL7ch3gcKgZ+ybND8MjZMnjf4whfPIwiJum3M6h+nTEzno8SjEqPJ8r/cEmsAkqC5IF991GQQAQ/FRwPBi701Q9J/31DLfnGuAb+pexFgTI1uosyY8pUGuoYyZN7k0odCMy6Ba4idLZuwJbop3mcY5zP5RnFQHFmAIH/Q2eHdTQzR4uoPUtw2vygrZEm9px5KNX8iEV3Vp2oacxT6rTAWd4pbLwRB/mMMcLScKvFp4Uy1TY8X9m8JxD97kf5XUNkfP+GnNubWSNi9tv/tm3wg6Y3r6yfhuD8CC8YPfTouCD8T/NCA58peiUg1Wg==", "Giorgia", "Mancini", "1234209876");

        
	INSERT INTO user_address(user_id, via, provincia, città, CAP)
		values
			("franco31@gmail.com", "Via Roma 12", "NA", "Palma Campania", "80036"),
			("1001", "Via Garibaldi 8", "MI", "Milano", "20121"),
			("1002", "Corso Vittorio Emanuele 45", "RM", "Roma", "00186"),
			("1003", "Via Nazionale 23", "PA", "Mazara del Vallo", "91026"),
			("1004", "Corso Italia 56", "TO", "Ivrea", "10015"),
			("1005", "Via della Repubblica 7", "FI", "Prato", "59100"),
			("1006", "Piazza San Marco 10", "VE", "Verona", "37121"),
			("1007", "Largo Argentina 14", "RM", "Napoli", "80132"),
			("1008", "Via del Corso 20", "RM", "Anzio", "00042"),
			("1009", "Via Garibaldi 25", "MI", "Monza", "20900"),
			("1010", "Via Roma 3", "NA", "Bari", "70121"),
			("1011", "Via Dante Alighieri 18", "FI", "Siena", "53100"),
			("ilaria53@gmail.com", "Piazza del Duomo 5", "MI", "Como", "22100"),
			("fabio64@gmail.com", "Via della Croce 7", "RM", "Milano", "20121"),
			("silvia75@gmail.com", "Corso Umberto 1", "TO", "Aosta", "11100"),
			("angelo86@gmail.com", "Via Toledo 30", "NA", "Caserta", "81100"),
			("giorgia97@gmail.com", "Piazza Navona 12", "RM", "Frosinone", "03100");
        
	INSERT INTO autori(nome, cognome, nArte)
    values("Hayao", "Miyazaki", null),
			("Masashi", "Kishimoto", "Luna Quill"),
            ("Peter", "Griffin", "Victory Wordsworth"),
            ("Paulo", "Coelho", "Dream Weaver"),
            ("Carlo", "Collodi", "Shadow Tale"),
            ("Umberto", "Eco", "Starry Script"),
            ("Charles", "Dickens", "Snowflake Sonnet"),
            ("William", "Shakespeare", null),
            ("Guglielmo", "Scuotilancia", "Rainbow Page"),
            ("Oscar", "Wilde", "Blossom Prose"),
            ("Jane", "Austen", "Light Story"),
            ("George", "Orwell", "Melody Paragraph"),
            ("Victor", "Hugo", "Cloud Verse"),
            ("Virginia", "Woolf", "Mirage Script"),
            ("Luigi", "Pirandello", "Dawn Stroke"),
            ("Cristiano", "Ronaldo", "CR7");
            
            
    
	INSERT INTO realizza(ID_autore, SKU)
		values	(10, 1000),
				(11, 1000),
				(12, 1001),
				(13, 1002),
				(14, 1003),
				(15, 1004),
				(16, 1005),
				(17, 1006),
				(18, 1007),
				(19, 1008),
				(20, 1009),
				(21, 1010),
				(22, 1011),
				(23, 1012),
				(24, 1000),
				(25, 1001),
                (23, 1019),
				(24, 1020),
				(25, 1021),
				(10, 1022),
				(11, 1023),
				(12, 1024),
				(14, 1025),
				(15, 1026),
				(16, 1027),
				(17, 1028),
				(10, 1029),
				(10, 1030),
				(11, 1031),
				(10, 1032),
				(10, 1033),
				(10, 1034),
				(10, 1035),
				(10, 1036),
				(10, 1037),
				(10, 1038),
				(10, 1039),
				(10, 1040),
				(10, 1041),
				(18, 1042),
				(18, 1043),
				(14, 1044),
				(15, 1045),
				(13, 1046),
				(12, 1047),
				(12, 1048),
				(11, 1049),
				(12, 1049);
        
 
 
	INSERT INTO order_details(data_pagamento, ID_pagamento, importo_pagamento, userId)
    values
    ("2023-10-29", 3143414, 52, 1000),
	("2023-10-30", 3143446, 65, "1001"),
	("2023-10-31", 3143417, 58, "1002"),
	("2023-11-01", 3143418, 48, "1003"),
	("2023-11-02", 3143419, 72, "1004"),
	("2023-10-30", 3143420, 65, "1001"),
	("2023-10-31", 3143421, 58, "1002"),
	("2023-11-01", 3143422, 48, "1003"),
	("2023-11-02", 3143423, 72, "1004"),
	("2023-11-13", 3143424, 58, "1005"),
	("2023-11-14", 3143425, 70, "1006"),
	("2023-11-15", 3143426, 45, "1007"),
	("2023-11-16", 3143427, 80, "1008"),
	("2023-11-17", 3143428, 55, "1009"),
	("2023-11-18", 3143429, 75, "1010"),
	("2023-11-19", 3143430, 40, "1011"),
	("2023-11-20", 3143431, 88, 1012),
	("2023-11-21", 3143432, 48, 1013),
	("2023-11-22", 3143433, 72, 1014),
	("2023-11-23", 3143434, 50, 1015),
	("2023-11-24", 3143435, 62, 1016);


    
    
    INSERT INTO order_items(ID_ordine, SKU, quantità)
    values
		(10, 1000, 3),
		(11, 1003, 2),
		(12, 1008, 5),
		(13, 1001, 9),
		(14, 1011, 1),
		(15, 1003,  3),
		(16, 1006,  6),
		(17, 1005, 3),
		(18, 1002, 2),
		(19, 1007, 1),
		(20, 1010, 8),
		(21, 1012, 10),
		(22, 1009, 4),
		(23, 1004, 5),
		(24, 1002, 2),
		(25, 1009, 9),
		(26, 1002, 2),
		(27, 1004,  4),
		(28, 1004, 4),
		(29, 1006, 6),
		(30, 1009, 9);


        
	INSERT INTO struttura(Materiale)
		values("PVC"),
			("Legno"),
            ("Metallo"),
            ("Ceramica"),
            ("Vetro"),
            ("Alluminio");
            
	INSERT INTO gadgets(SKU, modello, Marchio)
		values	(1013, 'OLED', 'Iron Man'),
			(1014, 'Funzionale', 'Back to the Future'),
			(1015, 'II Edizione', 'Men in Black'),
			(1016, 'v2', 'Star Wars'),
			(1017, 'ED. Bambini', 'Star Trek'),
			(1018, 'Modello LED', 'Doctor Who');

	INSERT INTO costituito	(SKU, Materiale)
		values (1013, 'Metallo'),
			(1014, 'Alluminio'),
			(1015, 'Vetro'),
			(1016, 'Metallo'),
			(1017, 'Ceramica'),
			(1018, "PVC");
            
insert into cart(user_id, total, created_at, modified_at) value(1000, 50, now(), now());
insert into cart_items(cart_id, SKU, quantity, insert_at, modified_at) values(10, 1000, 3, now(), now()),(10, 1001, 1, now(), now()); 
        
                              