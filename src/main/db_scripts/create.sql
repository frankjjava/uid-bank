-- Login as uidbank_tbls_user
-- psql uidbank_db uidbank_tbls_user

SET search_path TO uidbank_tbls;

CREATE SCHEMA uidbank_tbls;

GRANT USAGE ON SCHEMA uidbank_tbls TO uidbank_user;

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA uidbank_tbls TO uidbank_user;

GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA uidbank_tbls TO uidbank_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA uidbank_tbls GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO uidbank_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA uidbank_tbls GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO uidbank_user;

SET search_path TO uidbank_tbls;

   
CREATE TABLE UID_BANK_NAMES 
(
	BANK_NAME varchar(30)
);
