-- Tommy Li (tommy.li@firefire.co), 2017-01-13

-- To drop schema altogether, run commented statements first
-- REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA nem12 FROM nem12;
-- DROP SCHEMA IF EXISTS nem12 CASCADE;

GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE, REFERENCES, TRIGGER
ON ALL TABLES IN SCHEMA ${schemaName}
TO ${dbUserId};

ALTER USER ${dbUserId} SET search_path TO ${schemaName};

CREATE TABLE IF NOT EXISTS login (
  id          SERIAL        NOT NULL,
  username    VARCHAR(254)  NOT NULL,
  email       VARCHAR(254)  NOT NULL,
  name        VARCHAR(254),
  picture_url VARCHAR(254),
  locale      VARCHAR(254),
  family_name VARCHAR(254),
  given_name  VARCHAR(254),
  version     INT DEFAULT 0 NOT NULL,
  CONSTRAINT login_pk PRIMARY KEY (id),
  CONSTRAINT login_username_uk UNIQUE (username),
  CONSTRAINT login_email_uk UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS login_nmi (
  id      SERIAL        NOT NULL,
  login   INT           NOT NULL REFERENCES login (id),
  nmi     VARCHAR(11)   NOT NULL,
  label   VARCHAR(500),
  version INT DEFAULT 0 NOT NULL,
  CONSTRAINT login_nmi_pk PRIMARY KEY (id),
  CONSTRAINT login_nmi_uk UNIQUE (login, nmi)
);

CREATE TABLE IF NOT EXISTS nmi_meter_register (
  id                       SERIAL        NOT NULL,
  login_nmi                INT           NOT NULL REFERENCES login_nmi (id),
  meter_serial             VARCHAR(12)   NOT NULL,
  register_id              VARCHAR(10)   NOT NULL,
  nmi_suffix               VARCHAR(2)    NOT NULL,
  nmi_config               VARCHAR(240),
  mdm_data_stream_id       VARCHAR(2),
  uom                      VARCHAR(5)    NOT NULL,
  interval_length          INT           NOT NULL,
  next_scheduled_read_date DATE,
  version                  INT DEFAULT 0 NOT NULL,
  CONSTRAINT nmi_meter_register_pk PRIMARY KEY (id),
  CONSTRAINT nmi_meter_register_uk UNIQUE (login_nmi, meter_serial, register_id, nmi_suffix)
);
ALTER SEQUENCE nmi_meter_register_id_seq RESTART WITH 1000;

CREATE TABLE IF NOT EXISTS interval_day (
  id                   SERIAL        NOT NULL,
  nmi_meter_register   INT           NOT NULL REFERENCES nmi_meter_register (id),
  interval_date        DATE          NOT NULL,
  quality              VARCHAR(1)    NOT NULL,
  method               VARCHAR(2),
  reason_code          VARCHAR(3),
  reason_description   VARCHAR(240),
  update_date_time     TIMESTAMP,
  msats_load_date_time TIMESTAMP,
  version              INT DEFAULT 0 NOT NULL,
  CONSTRAINT interval_day_pk PRIMARY KEY (id),
  CONSTRAINT interval_day_uk UNIQUE (nmi_meter_register, interval_date) DEFERRABLE INITIALLY DEFERRED
);
ALTER SEQUENCE interval_day_id_seq RESTART WITH 1000 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS interval_value (
  interval_day       INT            NOT NULL REFERENCES interval_day (id),
  interval           INT            NOT NULL,
  value              NUMERIC(10, 4) NOT NULL,
  quality            VARCHAR(1)     NOT NULL,
  method             VARCHAR(2),
  reason_code        VARCHAR(3),
  reason_description VARCHAR(240),
  version            INT DEFAULT 0  NOT NULL,
  CONSTRAINT interval_value_pk PRIMARY KEY (interval_day, interval)
);

-- CREATE TABLE IF NOT EXISTS battery (
--   id              SERIAL        NOT NULL,
--   login_nmi       INT           NOT NULL REFERENCES login_nmi (id),
--   uom             VARCHAR(5)    NOT NULL,
--   interval_length INT           NOT NULL,
--   version         INT DEFAULT 0 NOT NULL,
--   CONSTRAINT battery_pk PRIMARY KEY (id),
--   CONSTRAINT battery_uk UNIQUE (login_nmi)
-- );
--
-- CREATE TABLE IF NOT EXISTS battery_interval_day (
--   id            SERIAL        NOT NULL,
--   battery       INT           NOT NULL REFERENCES battery (id),
--   interval_date DATE          NOT NULL,
--   version       INT DEFAULT 0 NOT NULL,
--   CONSTRAINT battery_interval_day_pk PRIMARY KEY (id),
--   CONSTRAINT battery_interval_day_uk UNIQUE (battery, interval_date) DEFERRABLE INITIALLY DEFERRED
-- );
--
-- CREATE TABLE IF NOT EXISTS battery_interval_value (
--   battery_interval_day INT            NOT NULL REFERENCES battery_interval_day (id),
--   interval             INT            NOT NULL,
--   charge               NUMERIC(10, 4) NOT NULL,
--   discharge            NUMERIC(10, 4) NOT NULL,
--   state_of_charge      NUMERIC(10, 4) NOT NULL,
--   version              INT DEFAULT 0  NOT NULL,
--   CONSTRAINT battery_interval_value_pk PRIMARY KEY (battery_interval_day, interval)
-- );
