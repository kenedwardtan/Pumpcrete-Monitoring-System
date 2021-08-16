--Popoulate billings

INSERT INTO billings (bill_no, client_id, client_name, project_name, project_add, date_doc, date_used,psc_id, concrete_struct, no_of_floors, quantity, unit_price, total, grand_total, posted, checked_by, approved_by, posted_by, received_by)
VALUES (1, 01, 'Sample Client Name', 'Sample Proj Name', '123 Sample Proj Address', '2021-01-01', '2021-02-01', 1, 'Sample Concrete Struct', 5, 2, 10.00, 20.00, 100.00, true, 'John Doe', 'Juan Dela Cruz', 'Jane Doe', 'Juanita Dela Cruz' )