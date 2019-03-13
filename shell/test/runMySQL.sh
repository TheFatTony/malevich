#!/usr/bin/env bash

docker volume create mysql_data

docker run -d -p 3306:3306 --name mysql --restart=unless-stopped \
-v mysql_data --env="MYSQL_ROOT_PASSWORD=Orion123" mysql:5.7.23 \
--character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci