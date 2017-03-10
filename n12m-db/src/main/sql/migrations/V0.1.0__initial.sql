-- Tommy Li (tommy.li@firefire.co), 2017-01-13

CREATE TABLE nmi_meter_register (
  id                       INT         NOT NULL AUTO_INCREMENT,
  nmi                      VARCHAR(11) NOT NULL,
  meter_serial             VARCHAR(12) NOT NULL,
  register_id              VARCHAR(10) NOT NULL,
  nmi_suffix               VARCHAR(2)  NOT NULL,
  nmi_config               VARCHAR(240),
  mdm_data_stream_id       VARCHAR(2),
  uom                      VARCHAR(5)  NOT NULL,
  interval_length          INT(3)      NOT NULL,
  next_scheduled_read_date DATE,
  version                  INT DEFAULT 0 NOT NULL,
  CONSTRAINT nmi_meter_register_pk PRIMARY KEY (id),
  CONSTRAINT nmi_meter_register_uk UNIQUE (nmi, meter_serial, register_id, nmi_suffix)
);
ALTER TABLE nmi_meter_register AUTO_INCREMENT = 1000;

CREATE TABLE interval_day (
  id                   INT        NOT NULL AUTO_INCREMENT,
  nmi_meter_register   INT        NOT NULL,
  interval_date        DATE       NOT NULL,
  quality_method       VARCHAR(3) NOT NULL,
  reason_code          VARCHAR(3),
  reason_description   VARCHAR(240),
  update_date_time     DATETIME,
  msats_load_date_time DATETIME,
  version              INT DEFAULT 0 NOT NULL,
  CONSTRAINT interval_day_pk PRIMARY KEY (id),
  CONSTRAINT interval_day_uk UNIQUE (nmi_meter_register, interval_date)
);
ALTER TABLE interval_day AUTO_INCREMENT = 1000;

CREATE TABLE interval_event (
  id                 INT        NOT NULL AUTO_INCREMENT,
  interval_day       INT        NOT NULL,
  start_interval     INT        NOT NULL,
  end_interval       INT        NOT NULL,
  quality_method     VARCHAR(3) NOT NULL,
  reason_code        VARCHAR(3),
  reason_description VARCHAR(240),
  version            INT DEFAULT 0 NOT NULL,
  CONSTRAINT interval_event_pk PRIMARY KEY (id),
  CONSTRAINT interval_event_uk UNIQUE (interval_day, start_interval)
);
ALTER TABLE interval_event AUTO_INCREMENT = 1000;
