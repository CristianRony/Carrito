create database CarritoAndroid
use CarritoAndroid
create table productos(
codPro char(4) primary key,
descripcion varchar(30) not null,
detalle varchar(300) not null,
stock int not null,
precio numeric(8,2) not null,
imagen varchar(30) not null)

insert into productos values('P001','Telivisor Plasma','Telivisor Plasma de 49" Smart con control automatico y wife incorporado ademas viene con rack de regalo',22,2500,'Televisor.jpg')
insert into productos values('P002','Minicomponente 800w','Minicomponente con potencia de salida de 800w,3 CD y lectura de USB para musica MP3 y Mp4 y Bluetooth',58,900,'Radio.jpg')
insert into productos values('P003','Cocina 4 Hornillas','Cocina 4 Hornillas con horno de 2 niveles,de regalo parrilla y 3 años de garantia por todo el equipo',78,1200,'Cocina.jpg')
insert into productos values('P004','DVD/Blu Ray','Blu Ray que permite lectura de CD/DVD/Blu Ray ultra HD especialmente creado para peliculas de alta definicion',26,350,'DVD.jpg')
insert into productos values('P005','Horno Multiuso','Facil manipulacion y tamaño adecuado permite al horno trabajar en cualquier ambiente y espacio reducido',44,350,'Horno.jpg')
insert into productos values('P006','Microondas Cheff','calienta y cocina gran variedad de platos dejando la comida en su punto exacto para el consumo',37,7500,'Microondas.jpg')
insert into productos values('P007','Plancha con Vaporizacion','Plancha con vaporizacion es especial para el planchado de ropa pasada como ternos o ropa de material grueso',98,1800,'Plancha.jpg')
insert into productos values('P008','Refrigerador','Con sistema de enfrentamiento rapido y ademas con separador de oloreste permite que no se mezclen los olores de los alimentos',36,2800,'Refrigerador.jpg')

select *from productos
create table usuarios(
codUsu int identity(1,1) primary key,
nomUsu varchar(30) not null,
correoUsu varchar(30) not null,
pasUsu varchar(30) not null)

insert into usuarios values('Jose Perez','Jose@gmail.com','123456');
insert into usuarios values('Ana Lopez','Ana@gmail.com','135790');

select *from usuarios