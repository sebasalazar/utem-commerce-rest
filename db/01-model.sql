BEGIN TRANSACTION;

--
-- Tabla para las credenciales usadas en el servicio
--
DROP TABLE IF EXISTS credentials CASCADE;
CREATE TABLE credentials (
	pk bigserial NOT NULL,
	token varchar(255) NOT NULL,
	app varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	active boolean DEFAULT '0',
	UNIQUE (token),
	PRIMARY KEY (pk)
);
CREATE UNIQUE INDEX ON credentials(UPPER(TRIM(both FROM app));

--
-- Tabla para almacenar productos.
--
DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products (
	pk bigserial NOT NULL,
	sku varchar(255) NOT NULL,
	name varchar(255) NOT NULL,
	UNIQUE (sku),
	PRIMARY KEY (pk)
);
CREATE INDEX ON products(name);


--
-- TABLA de ventas.
--
DROP TABLE IF EXISTS sales CASCADE;
CREATE TABLE sales (
	pk bigserial NOT NULL,
	product_fk bigint NOT NULL,
	quantity int NOT NULL DEFAULT '0',
	amount bigint NOT NULL DEFAULT '0',
	created timestamp NOT NULL,
	FOREIGN KEY (product_fk) REFERENCES products(pk) ON UPDATE CASCADE ON DELETE CASCADE,
	PRIMARY KEY (pk)
);
CREATE INDEX ON sales(product_fk);
CREATE INDEX ON sales(created);

COMMIT;




