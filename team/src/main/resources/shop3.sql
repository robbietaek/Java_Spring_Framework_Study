create table sale (
	saleid int(10) primary key,
	userid varchar(20),
	updatetime datetime
);

create table saleitem(
	saleid int(10),
	saleitemid int(3),
	itemid int(5),
	quantity int(3),
	primary key(saleid, saleitemid)
);

create table useraccount (
   userid varchar(10) primary key,
   password varchar(15),
   username varchar(20),
   phoneno varchar(20),
   postcode varchar(7),
   address varchar(30),
   email varchar(50),
   birthday datetime
);
