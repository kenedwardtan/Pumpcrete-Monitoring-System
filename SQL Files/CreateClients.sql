
DROP TABLE IF EXISTS client CASCADE;

CREATE TABLE client (
	 client_id BIGSERIAL NOT NULL PRIMARY KEY,
	 client_first_name VARCHAR NOT NULL,
	 client_last_name VARCHAR NOT NULL,
	 client_position VARCHAR NOT NULL,
	 client_address VARCHAR NOT NULL,
	 client_landline INTEGER NOT NULL,
	 client_cellphone BIGINT NOT NULL,
	 client_email VARCHAR NOT NULL,
	 latest_doc_date DATE);


 -- Modify initial value and increment
   ALTER SEQUENCE client_client_id_seq RESTART WITH 1 INCREMENT BY 1;
 
		
			
			