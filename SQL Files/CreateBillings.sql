
DROP TABLE IF EXISTS billings CASCADE;

CREATE TABLE billings (
	bill_no BIGSERIAL NOT NULL PRIMARY KEY,
	client_id BIGINT NOT NULL,
	project_name VARCHAR NOT NULL,
	project_add VARCHAR NOT NULL,
	date_doc DATE NOT NULL,
	date_used DATE NOT NULL,
	PSC_id BIGINT NOT NULL UNIQUE,
	conc_structure VARCHAR NOT NULL,
	floor_level BIGINT NOT NULL,
	qty NUMERIC NOT NULL,
	unit_price NUMERIC NOT NULL,
	total NUMERIC NOT NULL,
	posted BOOLEAN NOT NULL,
	in_payments BOOLEAN NOT NULL,
	is_paid BOOLEAN NOT NULL,
	edited_by VARCHAR NOT NULL REFERENCES users(username),
	posted_by VARCHAR REFERENCES users(username)
	);
	
	 -- Modify initial value and increment
   ALTER SEQUENCE billings_bill_no_seq RESTART WITH 1 INCREMENT BY 1;
 