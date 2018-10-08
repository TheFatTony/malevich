# Malevich



## Build commands

**Client:** 

mvn resources:resources clean install

**Server:** 

mvn resources:resources clean package -DskipTests=true

**Database:** 

mvn resources:resources liquibase:dropAll liquibase:update

## Tests

**VM arguments:**

-Dspring.profiles.active=local


## Docker Environment

**MySQL:** 

docker volume create mysql_data && docker run -d -p 3306:3306 \
-v mysql_data -v /tmp/conf/mysql/my.cnf:/etc/mysql/my.cnf --name mysql \
--restart=unless-stopped --env="MYSQL_ROOT_PASSWORD=Orion123" mysql:5.7.23

**Tomcat**

docker volume create tomcat_data && docker run -d -p 8080:8080 --restart=unless-stopped \
-v /tmp/conf/tomcat/webapps:/usr/local/tomcat/webapps \
-v /tmp/conf/tomcat/conf:/usr/local/tomcat/conf \
-v /tmp/conf/tomcat/logs:/usr/local/tomcat/logs \
-v tomcat_data --name tomcat tomcat:jre9-slim

**Hyperledger Composer**

docker volume create hyperledger_data && docker run -d -v hyperledger_data -p 9090:8080 \
--restart=unless-stopped hyperledger/composer-playground
