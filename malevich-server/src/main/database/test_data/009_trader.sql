INSERT INTO trader (id, person_id, user_id, description, mobile, date_of_birth, gender, thumbnail_id, description_ml)
VALUES (1,
        13,
        -1,
        'En: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
        '+1234567890',
        '1990-01-01',
        'm',
        4,
        JSON_OBJECT('en',
                    'En: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
                    'ru',
                    'Ru: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'));