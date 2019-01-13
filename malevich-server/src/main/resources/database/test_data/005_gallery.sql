INSERT INTO gallery (id, organization_id, thumbnail_id, image_id, description_ml)
VALUES (1,
        1,
        2,
        6,
        JSON_OBJECT('en',
                    'En: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
                    'ru',
                    'Ru: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'));