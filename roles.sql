--
-- PostgreSQL database cluster dump
--

-- Started on 2021-08-14 06:38:12

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

CREATE ROLE admin_role;
ALTER ROLE admin_role WITH NOSUPERUSER INHERIT CREATEROLE NOCREATEDB NOLOGIN NOREPLICATION NOBYPASSRLS;
CREATE ROLE admin_sample;
ALTER ROLE admin_sample WITH NOSUPERUSER INHERIT CREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:a6SAgtKPo/w/fqDAegq5xA==$xBTCN6m9wIUcQ+LMITNjqXBySWnBIwyL+AKfXIlVkXk=:gaDQ3O0nLG7uvSxy9xVrY9XxHOXhWSD1Lvs3PB+GszA=';
CREATE ROLE staff_role;
ALTER ROLE staff_role WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB NOLOGIN NOREPLICATION NOBYPASSRLS;
CREATE ROLE staff_sample;
ALTER ROLE staff_sample WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:8IdsqCHlOmRUt+Aj7BJQSw==$J9oFmzQGaZc3EOkQbnNpExe/EIZrAqG/r4Nf6Nl/daI=:/vk/HKsrMl3R0R3CLKI7rDzHiAckACWrtqiM1wiM33o=';
CREATE ROLE supervisor_role;
ALTER ROLE supervisor_role WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB NOLOGIN NOREPLICATION NOBYPASSRLS;
CREATE ROLE supervisor_sample;
ALTER ROLE supervisor_sample WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:tQx2pmiL0XiPoni3vIAGXQ==$liZzNmxqpEnobKTds+BK8iDPI+QwdVyeLaKE3UHtVsw=:a0gPnsueAJnPULHEPDGKQlzHMEqJo1YDLN1mkHjZfTw=';


--
-- Role memberships
--

GRANT admin_role TO admin_sample GRANTED BY postgres;
GRANT staff_role TO staff_sample GRANTED BY postgres;
GRANT supervisor_role TO supervisor_sample GRANTED BY postgres;

-- GRANT ACCESS TO DB
--ADMIN
GRANT UPDATE on "users" to admin_role; --admin can update
GRANT INSERT on "users" to admin_role; --admin can create new
GRANT SELECT on "users" to admin_role; --admin can view users


--STAFF
--UPDATE AND VIEW PASSWORD
GRANT UPDATE on "users" to staff_role;
GRANT SELECT on "users" to staff_role;
--CLIENTS
GRANT UPDATE on "client" to staff_role;
GRANT INSERT on "client" to staff_role; 
GRANT SELECT on "client" to staff_role; 
GRANT DELETE on "client" to staff_role; 
--CONTACT_DETAILS
GRANT UPDATE on "contact_details" to staff_role;
GRANT INSERT on "contact_details" to staff_role; 
GRANT SELECT on "contact_details" to staff_role; 
GRANT DELETE on "contact_details" to staff_role; 
--BILLINGS
GRANT UPDATE on "billings" to staff_role;
GRANT INSERT on "billings" to staff_role; 
GRANT SELECT on "billings" to staff_role; 
GRANT DELETE on "billings" to staff_role; 
--PUMPCRETE
GRANT UPDATE on "pumpcretes" to staff_role;
GRANT INSERT on "pumpcretes" to staff_role; 
GRANT SELECT on "pumpcretes" to staff_role; 
GRANT DELETE on "pumpcretes" to staff_role; 
--CREDIT MEMOS
GRANT UPDATE on "credit_memo" to staff_role;
GRANT INSERT on "credit_memo" to staff_role; 
GRANT SELECT on "credit_memo" to staff_role; 
GRANT DELETE on "credit_memo" to staff_role; 

--SUPERVISOR
GRANT UPDATE on "users" to supervisor_role;
GRANT SELECT on "users" to supervisor_role;
--CLIENTS
GRANT UPDATE on "client" to supervisor_role;
GRANT SELECT on "client" to supervisor_role; 
--CONTACT_DETAILS
GRANT UPDATE on "contact_details" to supervisor_role;
GRANT SELECT on "contact_details" to supervisor_role; 
--BILLINGS
GRANT UPDATE on "billings" to supervisor_role;
GRANT SELECT on "billings" to supervisor_role; 
--PUMPCRETE
GRANT UPDATE on "pumpcretes" to supervisor_role;
GRANT SELECT on "pumpcretes" to supervisor_role; 
--CREDIT MEMOS
GRANT UPDATE on "credit_memo" to supervisor_role;
GRANT SELECT on "credit_memo" to supervisor_role; 


-- Completed on 2021-08-14 06:38:12

--
-- PostgreSQL database cluster dump complete
--

