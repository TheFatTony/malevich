CREATE USER 'mlv'@'localhost'
  IDENTIFIED WITH mysql_native_password BY 'g4sGfdTbT23';
CREATE USER 'mlv'@'%'
  IDENTIFIED WITH mysql_native_password BY 'g4sGfdTbT23';

CREATE DATABASE mlv
  CHARACTER SET utf8
  COLLATE utf8_general_ci;

GRANT ALL PRIVILEGES ON mlv.* TO 'mlv'@'localhost';
GRANT ALL PRIVILEGES ON mlv.* TO 'mlv'@'%';

CREATE USER 'mlv_user'@'localhost'
  IDENTIFIED WITH mysql_native_password BY 'tmuL4svR76d';
CREATE USER 'mlv_user'@'%'
  IDENTIFIED WITH mysql_native_password BY 'tmuL4svR76d';

GRANT DELETE ON mlv.* TO 'mlv_user'@'localhost';
GRANT DELETE ON mlv.* TO 'mlv_user'@'%';
GRANT INSERT ON mlv.* TO 'mlv_user'@'localhost';
GRANT INSERT ON mlv.* TO 'mlv_user'@'%';
GRANT SELECT ON mlv.* TO 'mlv_user'@'localhost';
GRANT SELECT ON mlv.* TO 'mlv_user'@'%';
GRANT UPDATE ON mlv.* TO 'mlv_user'@'localhost';
GRANT UPDATE ON mlv.* TO 'mlv_user'@'%';