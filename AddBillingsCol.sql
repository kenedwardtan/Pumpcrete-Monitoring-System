
	
DROP TABLE IF EXISTS billings CASCADE;

CREATE TABLE billings (
	bill_no BIGSERIAL NOT NULL PRIMARY KEY,
	client_id INT NOT NULL,
	client_name VARCHAR(50) NOT NULL,
	project_name VARCHAR(50) NOT NULL,
	project_add VARCHAR(50) NOT NULL,
	date_doc DATE NOT NULL,
	date_used DATE NOT NULL,
	PSC_id INT NOT NULL,
	concrete_struct VARCHAR(60) NOT NULL,
	no_of_floors INT NOT NULL,
	quantity NUMERIC NOT NULL,
	unit_price NUMERIC NOT NULL,
	total NUMERIC NOT NULL,
	grand_total NUMERIC NOT NULL,
	posted BOOLEAN NOT NULL,
	checked_by VARCHAR(50),
	approved_by VARCHAR(50),
	posted_by VARCHAR(50),
	received_by VARCHAR(50)
	);
	
	