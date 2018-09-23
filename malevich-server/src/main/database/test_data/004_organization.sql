INSERT INTO organization (id, legal_name, location, legal_name_ml, location_ml)
VALUES (1,
        'Gallery 1',
        'London, England',
        JSON_OBJECT('en', 'Gallery 1', 'ru', 'Галерея 1'),
        JSON_OBJECT('en', 'London, England', 'ru', 'Лондон, Англия'));