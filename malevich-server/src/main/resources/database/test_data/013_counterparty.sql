INSERT INTO
  counterparty (id, type_id, user_id, is_organization, is_gallery, person_id, organization_id, gallery_id, image_id)
VALUES
  (1, 'X', null, false, false, null, null, null, null),
  (2, 'G', 3, true, true, null, 1, 1, 6),
  (3, 'T', 5, false, false, 1, null, null, 4),
  (4, 'T', 6, false, false, 2, null, null, 4);