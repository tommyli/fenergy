-- Tommy Li (tommy.li@firefire.co), 2017-02-09

-- INSERT INTO nmi_meter_register(nmi, meter_serial, register_id, nmi_suffix, nmi_config, mdm_data_stream_id, uom, interval_length, next_scheduled_read_date)
-- VALUES ('6123456789', 'ABCD-12345', 'E1', 'E1', 'E1E1', NULL, 'KWH', 30, DATE '2017-02-23');

-- INSERT INTO interval_day(nmi_meter_register, interval_date, quality_method, reason_code, reason_description, update_date_time, msats_load_date_time, values)
-- VALUES (currval('nmi_meter_register_id_seq'), DATE '2017-01-01', 'A', 'ABC', 'ABC reason', TIMESTAMP '2016-12-30 20:28:28', TIMESTAMP '2016-12-31 20:28:28', '0.0,1.1,2.2,3.3,4.4,5.5,6.6,7.7,8.8,9.9,11.0,12.1,13.2,14.3,15.4,16.5,17.6,-18.7,19.8,20.9,22.0,23.1,24.2,25.3,26.4,27.5,28.6,29.7,30.8,31.9,33.0,34.1,35.2,36.3,37.4,-38.5,39.6,40.7,41.8,42.9,44.0,45.1,46.2,47.3,48.4,49.5,50.6,51.7');
-- INSERT INTO interval_day(nmi_meter_register, interval_date, quality_method, reason_code, reason_description, update_date_time, msats_load_date_time, values)
-- VALUES (currval('nmi_meter_register_id_seq'), DATE '2017-01-02', 'A', NULL, NULL, TIMESTAMP '2016-12-31 20:28:28', TIMESTAMP '2017-01-01 20:28:28', '0.0,-1.2,2.4,3.6,4.8,6.0,7.2,8.4,9.6,10.8,12.0,13.2,14.4,15.6,16.8,18.0,19.2,20.4,21.6,22.8,24.0,25.2,26.4,27.6,28.8,30.0,31.2,32.4,33.6,34.8,36.0,37.2,38.4,39.6,40.8,42.0,43.2,44.4,45.6,46.8,48.0,49.2,50.4,51.6,52.8,54.0,55.2,-56.4');

INSERT INTO nmi_meter_register(nmi, meter_serial, register_id, nmi_suffix, nmi_config, mdm_data_stream_id, uom, interval_length, next_scheduled_read_date)
VALUES ('6123456789', 'ABCD-12345', 'E1', 'E1', 'E1E1', NULL, 'KWH', 30, DATE '2017-02-23');

INSERT INTO interval_day(nmi_meter_register, interval_date, quality_method, reason_code, reason_description, update_date_time, msats_load_date_time, values)
VALUES (currval('nmi_meter_register_id_seq'), DATE '2017-01-01', 'A', 'ABC', 'ABC reason', TIMESTAMP '2016-12-30 20:28:28', TIMESTAMP '2016-12-31 20:28:28', '0.0,1.1,2.2,3.3,4.4,5.5,6.6,7.7,8.8,9.9,11.0,12.1,13.2,14.3,15.4,16.5,17.6,-18.7,19.8,20.9,22.0,23.1,24.2,25.3,26.4,27.5,28.6,29.7,30.8,31.9,33.0,34.1,35.2,36.3,37.4,-38.5,39.6,40.7,41.8,42.9,44.0,45.1,46.2,47.3,48.4,49.5,50.6,51.7');

INSERT INTO interval_day(nmi_meter_register, interval_date, quality_method, reason_code, reason_description, update_date_time, msats_load_date_time, values)
VALUES (currval('nmi_meter_register_id_seq'), DATE '2017-01-02', 'V', NULL, NULL, TIMESTAMP '2016-12-31 20:28:28', TIMESTAMP '2017-01-01 20:28:28', '0.0,-1.2,2.4,3.6,4.8,6.0,7.2,8.4,9.6,10.8,12.0,13.2,14.4,15.6,16.8,18.0,19.2,20.4,21.6,22.8,24.0,25.2,26.4,27.6,28.8,30.0,31.2,32.4,33.6,34.8,36.0,37.2,38.4,39.6,40.8,42.0,43.2,44.4,45.6,46.8,48.0,49.2,50.4,51.6,52.8,54.0,55.2,-56.4');
INSERT INTO interval_event(interval_day, start_interval, end_interval, quality_method, reason_code, reason_description)
VALUES (currval('interval_day_id_seq'), 1, 13, 'A', NULL, NULL);
INSERT INTO interval_event(interval_day, start_interval, end_interval, quality_method, reason_code, reason_description)
VALUES (currval('interval_day_id_seq'), 14, 48, 'E', NULL, NULL);

INSERT INTO interval_day(nmi_meter_register, interval_date, quality_method, reason_code, reason_description, update_date_time, msats_load_date_time, values)
VALUES (currval('nmi_meter_register_id_seq'), DATE '2017-01-03', 'A', '79', NULL, TIMESTAMP '2017-01-01 20:28:28', TIMESTAMP '2017-01-02 20:28:28', '0.0,-1.2,-2.4,3.6,4.8,6.0,7.2,8.4,9.6,10.8,12.0,13.2,14.4,15.6,16.8,18.0,19.2,20.4,21.6,22.8,24.0,25.2,26.4,27.6,28.8,30.0,31.2,32.4,33.6,34.8,36.0,37.2,38.4,39.6,40.8,42.0,43.2,44.4,45.6,46.8,48.0,49.2,50.4,51.6,52.8,54.0,55.2,-56.4');
INSERT INTO interval_event(interval_day, start_interval, end_interval, quality_method, reason_code, reason_description)
VALUES (currval('interval_day_id_seq'), 1, 20, 'A', NULL, NULL);
INSERT INTO interval_event(interval_day, start_interval, end_interval, quality_method, reason_code, reason_description)
VALUES (currval('interval_day_id_seq'), 21, 48, 'E', '0', 'broken meter');
