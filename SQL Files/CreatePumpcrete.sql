DROP TABLE IF EXISTS pumpcrete CASCADE;

CREATE TABLE pumpcrete (
	 pumpcrete_id BIGSERIAL NOT NULL PRIMARY KEY,
	 description VARCHAR NOT NULL,
	 plate_no VARCHAR NOT NULL UNIQUE,
	 fuel_type VARCHAR NOT NULL,
	 purchase_date DATE NOT NULL,
	 cr_no BIGINT UNIQUE,
	 or_no BIGINT UNIQUE,
	 tires INTEGER NOT NULL,
	 rented BOOLEAN NOT NULL,
	 client_name VARCHAR );


 -- Modify initial value and increment
   ALTER SEQUENCE pumpcrete_pumpcrete_id_seq RESTART WITH 1 INCREMENT BY 1;
 
		
			
			