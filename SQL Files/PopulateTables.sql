--Populate Users
INSERT INTO users(username, email, first_name, middle_name, last_name, role, password)
VALUES ('staff_sample','staff@gmail.com', 'Staff', 'Sample', 'Data', 'Staff', 'staffpass');
INSERT INTO users(username, email, first_name, middle_name, last_name, role, password)
VALUES ('supervisor_sample','supervisor@gmail.com', 'Supervisor', 'Sample', 'Data', 'Supervisor', 'supervisorpass');
INSERT INTO users(username, email, first_name, middle_name, last_name, role, password)
VALUES ('admin_sample','admin@gmail.com', 'Admin', 'Sample', 'Data', 'Admin', 'adminpass');


--Populate Clients
INSERT INTO client (client_first_name, client_last_name, client_position, client_address, client_landline, client_cellphone, client_email,latest_doc_date)
VALUES ('A Client', 'Name 1', 'Position 1', 'Address 1', 123, 123, 'client1@gmail.com','2021-01-01');
INSERT INTO client (client_first_name, client_last_name, client_position, client_address, client_landline, client_cellphone, client_email,latest_doc_date)
VALUES ( 'B Client','Name 2', 'Position 2', 'Address 2', 234, 234, 'client2@gmail.com','2021-03-03');
INSERT INTO client (client_first_name, client_last_name, client_position, client_address, client_landline, client_cellphone, client_email,latest_doc_date)
VALUES ('C Client','Name 3', 'Position 3', 'Address 3', 345, 345, 'client3@gmail.com','2021-04-04');

--Popoulate billings

INSERT INTO billings ( client_id, project_name, project_add, date_doc, date_used, psc_id, conc_structure,floor_level, qty, unit_price,total, posted, posted_by, filled_by)
VALUES ( 1, 'PROJ 1', 'PROJ 1 ADDRESS','2021-01-01', '2021-01-01', 1 , 'Concrete Structure 1', 5, 50, 10.00, 500.00, true,'supervisor_sample', 'staff_sample' );
INSERT INTO billings (  client_id, project_name, project_add, date_doc, date_used, psc_id, conc_structure,floor_level, qty, unit_price,total, posted, posted_by, filled_by)
VALUES ( 2, 'PROJ 2', 'PROJ 2 ADDRESS','2021-02-02', '2021-02-02', 2, 'Concrete Structure 2', 5, 50, 10.00, 500.00, true, 'supervisor_sample', 'staff_sample' );
INSERT INTO billings (  client_id, project_name, project_add,  date_doc, date_used,psc_id, conc_structure,floor_level, qty, unit_price,total, posted, posted_by, filled_by)
VALUES ( 2, 'PROJ 3', 'PROJ 3 ADDRESS', '2021-03-03', '2021-03-03', 3,   'Concrete Structure 3', 5, 50, 10.00, 500.00, true, 'supervisor_sample', 'staff_sample' );
INSERT INTO billings (  client_id, project_name, project_add,  date_doc, date_used,psc_id, conc_structure,floor_level, qty, unit_price,total, posted, posted_by, filled_by)
VALUES ( 3, 'PROJ 4', 'PROJ 4 ADDRESS', '2021-04-04', '2021-04-04', 4,  'Concrete Structure 4', 5, 50, 10.00, 500.00, true, 'supervisor_sample', 'staff_sample' );


--Populate Pumpcrete
INSERT INTO pumpcrete (description, plate_no, fuel_type,purchase_date,cr_no, or_no, tires, rented)
VALUES ('Description 1', 'ABC123', 'Diesel 1', '2021-01-01', 1, 1, 8, false);
INSERT INTO pumpcrete (description, plate_no, fuel_type,purchase_date,cr_no, or_no, tires, rented,client_name)
VALUES ('Description 2', 'DEF456', 'Diesel 2', '2021-02-02', 2, 2, 8, true, 'A Client Name 1' );
INSERT INTO pumpcrete (description, plate_no, fuel_type,purchase_date,cr_no, or_no, tires, rented,client_name)
VALUES ('Description 3', 'GHI789', 'Diesel 3', '2021-02-02', 3, 3, 8, true, 'B Client Name 2' );


--Populate Collection
INSERT INTO collections (date,collection_no,client_id,bill_no, pumpcrete_id, posted, grand_total, check_number, check_date, bank)
VALUES('2021-01-01',1, 1,1, 2, false, 500.00, '1111', '2021-01-01', 'Bank 1');
INSERT INTO collections (date,collection_no,client_id,bill_no, pumpcrete_id, posted, grand_total, check_number, check_date, bank)
VALUES('2021-02-02',1, 2,2, 3, false, 1000.00, '1111', '2021-01-01', 'Bank 2');
INSERT INTO collections (date,collection_no,client_id,bill_no, pumpcrete_id, posted, grand_total, check_number, check_date, bank)
VALUES('2021-02-03',2, 2, 3,1, false, 1000.00, '1111', '2021-01-01', 'Bank 3');

