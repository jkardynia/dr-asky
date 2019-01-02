INSERT INTO location (id, name, city, street, house_number, lat, lng) VALUES
  (1, 'main square', 'Cracow', 'ul. Rynek', '1', '50.062023', '19.936422'),
  (2, 'home', 'Cracow', 'Ul. Opolska', '1', '50.091593', '19.934426');

INSERT INTO profile(id, home_location_id, username) values(1, 2, 'default');

INSERT INTO intent_find_bus (id, profile_id) VALUES(1, 1);
INSERT INTO Intent_Find_Bus_Locations (intent_find_bus_id, locations_id) VALUES(1, 1);
INSERT INTO Intent_Find_Bus_Locations (intent_find_bus_id, locations_id) VALUES(1, 2);

INSERT INTO intent_air_quality (id, profile_id) VALUES(1, 1);
INSERT INTO intent_air_quality_locations (intent_air_quality_id, locations_id) VALUES(1, 1);
INSERT INTO intent_air_quality_locations (intent_air_quality_id, locations_id) VALUES(1, 2);