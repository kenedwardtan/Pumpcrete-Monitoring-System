

DROP TABLE IF EXISTS collections CASCADE;

CREATE TABLE collections (
	collection_no BIGSERIAL PRIMARY KEY, 
	date DATE NOT NULL, 
	/*collection_no BIGINT NOT NULL,*/
	client_id BIGINT NOT NULL REFERENCES client(client_id),
	bill_no VARCHAR NOT NULL,
	pumpcrete_id BIGINT NOT NULL REFERENCES pumpcrete(pumpcrete_id),
	posted BOOLEAN NOT NULL,
	grand_total NUMERIC NOT NULL, 
	check_number BIGINT NOT NULL,
	check_date DATE NOT NULL,
	bank VARCHAR NOT NULL,
	edited_by VARCHAR NOT NULL REFERENCES users(username),
	posted_by VARCHAR REFERENCES users(username)
	 );


 -- Modify initial value and increment
   ALTER SEQUENCE collections_collection_no_seq RESTART WITH 1 INCREMENT BY 1;
 
		
			