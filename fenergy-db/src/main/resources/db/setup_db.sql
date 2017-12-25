-- Tommy Li (tommy.li@firefire.co), 2017-05-13

-- Initial DB setup script for test and production, run as postgres user
DROP DATABASE IF EXISTS fenergyprod;
DROP DATABASE IF EXISTS fenergytest;
CREATE DATABASE fenergyprod;
CREATE DATABASE fenergytest;

DROP USER IF EXISTS fenergy;
DROP USER IF EXISTS fenergyread;
DROP USER IF EXISTS fenergytest;
DROP USER IF EXISTS fenergytestread;

CREATE USER fenergy WITH ENCRYPTED PASSWORD 'changeme_fenergy_password';
CREATE USER fenergyread WITH ENCRYPTED PASSWORD 'changeme_fenergyread_password';
CREATE USER fenergytest WITH ENCRYPTED PASSWORD 'fenergytest_password';
CREATE USER fenergytestread WITH ENCRYPTED PASSWORD 'fenergytestread_password';

GRANT CONNECT, CREATE, TEMPORARY ON DATABASE fenergyprod TO fenergy;
GRANT CONNECT ON DATABASE fenergyprod TO fenergyread;
GRANT CONNECT, CREATE, TEMPORARY ON DATABASE fenergytest TO fenergytest;
GRANT CONNECT ON DATABASE fenergytest TO fenergytestread;

COMMIT;
