GRANT UPDATE on "users" to admin_role; --admin can update
GRANT INSERT on "users" to admin_role; --admin can create new
GRANT SELECT on "users" to admin_role; --admin can view users


--UPDATE AND VIEW PASSWORD
GRANT UPDATE on "users" to staff_role;
GRANT SELECT on "users" to staff_role;
--CLIENTS
GRANT UPDATE on "client" to staff_role;
GRANT INSERT on "client" to staff_role; 
GRANT SELECT on "client" to staff_role; 
GRANT DELETE on "client" to staff_role; 
--BILLINGS
GRANT UPDATE on "billings" to staff_role;
GRANT INSERT on "billings" to staff_role; 
GRANT SELECT on "billings" to staff_role; 
GRANT DELETE on "billings" to staff_role; 
--PUMPCRETE
GRANT UPDATE on "pumpcrete" to staff_role;
GRANT INSERT on "pumpcrete" to staff_role; 
GRANT SELECT on "pumpcrete" to staff_role; 
GRANT DELETE on "pumpcrete" to staff_role; 
--CREDIT MEMOS
GRANT UPDATE on "collections" to staff_role;
GRANT INSERT on "collections" to staff_role; 
GRANT SELECT on "collections" to staff_role; 
GRANT DELETE on "collections" to staff_role; 


--UPDATE AND VIEW PASSWORD
GRANT UPDATE on "users" to supervisor_role;
GRANT SELECT on "users" to supervisor_role;
--CLIENTS
GRANT UPDATE on "client" to supervisor_role;
GRANT SELECT on "client" to supervisor_role; 
--BILLINGS
GRANT UPDATE on "billings" to supervisor_role;
GRANT SELECT on "billings" to supervisor_role; 
--PUMPCRETE
GRANT UPDATE on "pumpcrete" to supervisor_role;
GRANT SELECT on "pumpcrete" to supervisor_role; 
--CREDIT MEMOS
GRANT UPDATE on "collections" to supervisor_role;
GRANT SELECT on "collections" to supervisor_role; 

--ACCESS TO ID
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO staff_role;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO supervisor_role;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO admin_role;


