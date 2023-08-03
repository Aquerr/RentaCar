CREATE TABLE vehicle_pickup_location (
    id IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    lang_code VARCHAR(2) NOT NULL,
    latitude REAL NOT NULL,
    longtitude REAL NOT NULL
);

INSERT INTO vehicle_pickup_location (id, name, city, lang_code, latitude, longtitude) VALUES
(1, 'Palace of Culture and Science', 'Warsaw', 'en', 52.23183, 21.00598),
(2, 'Warsaw Uprising Museum', 'Warsaw', 'en', 52.23283, 20.98086),
(3, 'Copernicus Science Centre', 'Warsaw', 'en', 52.24184, 21.02872),
(4, 'Warsaw Chopin Airport', 'Warsaw', 'en', 52.16432, 20.96966),
(5, 'St. Mary''s Basilica', 'Krakow', 'en', 50.06166, 19.93939),
(6, 'Katowice Gallery', 'Katowice', 'en', 50.25933, 19.01794),
(7, 'Galeria Dominikanska', 'Wroclaw', 'en', 51.10852, 17.04041),
(8, 'The Royal Castle in Warsaw', 'Warsaw', 'en', 52.24796, 21.01525),
(9, 'Pałac Kultury i Nauki', 'Warszawa', 'pl', 52.23183, 21.00598),
(10, 'Muzeum Powstania Warszawskiego', 'Warszawa', 'pl', 52.23283, 20.98086),
(11, 'Centrum Nauki Kopernik', 'Warszawa', 'pl', 52.24184, 21.02872),
(12, 'Lotnisko Chopina Warszawa', 'Warszawa', 'pl', 52.16432, 20.96966),
(13, 'Bazylika Mariacka', 'Kraków', 'pl', 50.06166, 19.93939),
(14, 'Galeria Katowicka', 'Katowice', 'pl', 50.25933, 19.01794),
(15, 'Galeria Dominikanska', 'Wrocław', 'pl', 51.10852, 17.04041),
(16, 'Zamek Królewski w Warszawie', 'Warszawa', 'pl', 52.24796, 21.01525);