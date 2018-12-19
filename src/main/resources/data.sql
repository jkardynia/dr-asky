INSERT INTO Profile(id, home_location, username) values(1, 'Pachonskiego 14', 'default');
INSERT INTO Profile(id, home_location, username) values(2, 'Pachonskiego 14', 'John');

INSERT INTO destination (id, alias, address, profile_id) VALUES
  (1, 'home', 'ul Fabryczna 23, 32-333 Krakow', 1),
  (2, 'friend', 'St Paul Street, 2345 Glasgow', 1),
  (3, 'office', 'St Office, 2345 Berlin', 2);