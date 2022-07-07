-- template_service ----------------------------------------------------------------------------------------------------
INSERT INTO main.template_service (interval_to_order, name, composite) VALUES (15, 'Мойка', false);
-- template_option -----------------------------------------------------------------------------------------------------
INSERT INTO main.template_option (name, required, view_type, template_service_id) VALUES ('Быстрая', true, 0, 1);
-- template_value ------------------------------------------------------------------------------------------------------
INSERT INTO main.template_value (id, duration, price, value, template_option_id) VALUES (1, 30, 1500, 'ДА', 1);
-- car -----------------------------------------------------------------------------------------------------------------
INSERT INTO main.car (model, removed, state_number, tire_diameter, color_id, usr_id) VALUES ('Toyota', false, 'АН7707АС', 17, 1, 1);
-- booking -------------------------------------------------------------------------------------------------------------
INSERT INTO main.booking (appointment_date, closed_date, order_status, price, registration_date, car_id, usr_id) VALUES ('2022-06-10 10:00:00.000000', '2022-06-10 10:30:00.000000', 0, 1000, '2022-06-10 19:40:07.000000', 1, 1);
INSERT INTO main.booking (appointment_date, closed_date, order_status, price, registration_date, car_id, usr_id) VALUES ('2022-06-10 12:00:00.000000', '2022-06-10 12:45:00.000000', 0, 2000, '2022-06-10 19:40:38.000000', 1, 1);
INSERT INTO main.booking (appointment_date, closed_date, order_status, price, registration_date, car_id, usr_id) VALUES ('2022-06-10 12:30:00.000000', '2022-06-10 12:45:00.000000', 0, 3000, '2022-06-10 19:41:14.000000', 1, 1);
INSERT INTO main.booking (appointment_date, closed_date, order_status, price, registration_date, car_id, usr_id) VALUES ('2022-06-10 12:45:00.000000', '2022-06-10 13:00:00.000000', 0, 4000, '2022-06-10 19:41:59.000000', 1, 1);
INSERT INTO main.booking (appointment_date, closed_date, order_status, price, registration_date, car_id, usr_id) VALUES ('2022-06-10 16:00:00.000000', '2022-06-10 17:00:00.000000', 0, 5000, '2022-06-10 19:41:59.000000', 1, 1);
INSERT INTO main.booking (appointment_date, closed_date, order_status, price, registration_date, car_id, usr_id) VALUES ('2022-06-10 19:15:00.000000', '2022-06-10 19:30:00.000000', 0, 7000, '2022-06-10 19:41:59.000000', 1, 1);
INSERT INTO main.booking (appointment_date, closed_date, order_status, price, registration_date, car_id, usr_id) VALUES ('2022-06-10 18:15:00.000000', '2022-06-10 19:00:00.000000', 0, 6000, '2022-06-10 19:41:59.000000', 1, 1);
INSERT INTO main.booking (appointment_date, closed_date, order_status, price, registration_date, car_id, usr_id) VALUES ('2022-06-10 17:00:00.000000', '2022-06-10 17:15:00.000000', 0, 8000, '2022-06-10 19:41:59.000000', 1, 1);
INSERT INTO main.booking (appointment_date, closed_date, order_status, price, registration_date, car_id, usr_id) VALUES ('2022-06-14 11:30:00.000000', '2022-06-14 13:00:00.000000', 0, 9000, '2022-06-22 11:33:05.000000', 1, 1);


