INSERT INTO location (id, name, city, street, house_number, lat, lng) VALUES
  (1, 'sausage', 'Cracow', 'ul. Rynek', '1', '50.062023', '19.936422'),
  (2, 'home', 'Cracow', 'Ul. Opolska', '1', '50.091593', '19.934426');

INSERT INTO profile(id, home_location_id, username, timezone) values(1, 2, 'default', 'US/Hawaii');

INSERT INTO intent_find_bus (id, profile_id) VALUES(1, 1);
INSERT INTO Intent_Find_Bus_Locations (intent_find_bus_id, locations_id) VALUES(1, 1);
INSERT INTO Intent_Find_Bus_Locations (intent_find_bus_id, locations_id) VALUES(1, 2);

INSERT INTO intent_air_quality (id, profile_id) VALUES(1, 1);
INSERT INTO intent_air_quality_locations (intent_air_quality_id, locations_id) VALUES(1, 1);
INSERT INTO intent_air_quality_locations (intent_air_quality_id, locations_id) VALUES(1, 2);

INSERT INTO bus_route (id, direction, line_number, stop_name, from_id, to_id) VALUES(1, 'Prądnik Biały - Rżąka', '144', 'Pachońskiego', 2, 1);
