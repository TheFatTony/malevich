#!/usr/bin/env bash

export LC_ALL=en_US.UTF-8
docker volume create tomcat_data && docker run -d -p 80:8080 --restart=unless-stopped \
-v /tmp/conf/tomcat/webapps:/usr/local/tomcat/webapps \
-v /tmp/conf/tomcat/conf:/usr/local/tomcat/conf \
-v /tmp/conf/tomcat/logs:/usr/local/tomcat/logs \
-v tomcat_data --name tomcat tomcat:jre9-slim


docker volume create mysql_data && docker run -d -p 3306:3306 \
-v mysql_data -v /tmp/conf/mysql/my.cnf:/etc/mysql/my.cnf --name mysql \
--restart=unless-stopped --env="MYSQL_ROOT_PASSWORD=Orion123" mysql:5.7.23
