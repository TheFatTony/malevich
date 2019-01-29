insert into gallery
    (id, organization_id, thumbnail_id, image_id, title_ml, description_ml)
values
    (2 ,1, null, null, JSON_OBJECT('en', 'Gallery 1', 'ru', 'Галерея 1'),
    JSON_OBJECT('en',
                    'En: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.',
                    'ru',
                    'Ru: Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'));