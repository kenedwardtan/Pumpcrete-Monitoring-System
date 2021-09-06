

DROP TABLE IF EXISTS collections CASCADE;

CREATE TABLE collections (
	collection_ref BIGSERIAL PRIMARY KEY, 
	date DATE NOT NULL, 
	collection_no BIGINT NOT NULL,
	client_id BIGINT NOT NULL REFERENCES client(client_id),
	bill_no BIGINT NOT NULL	REFERENCES billings(bill_no),
	pumpcrete_id BIGINT NOT NULL REFERENCES pumpcrete(pumpcrete_id),
	posted BOOLEAN NOT NULL,
	grand_total NUMERIC NOT NULL, 
	check_number BIGINT NOT NULL,
	check_date DATE NOT NULL,
	bank VARCHAR NOT NULL
	 );


 -- Modify initial value and increment
   ALTER SEQUENCE collections_collection_ref_seq RESTART WITH 1 INCREMENT BY 1;
 
		
			