# Malevich



## Server

**Build command:**

resources:resources clean liquibase:updateSQL package -DskipTests=true

**Tests**

To test add profile to VM arguments:

-Dspring.profiles.active=local 

## Mobile

## Web

## Network

## Docker Environment

### MySQL
docker volume create mysql_data
docker run -d -p 3306:3306 -v mysql_data --restart=unless-stopped --env="MYSQL_ROOT_PASSWORD=Orion123" mysql:5.7.23

### Tomcat
docker volume create tomcat_data
docker run -d -p 8080:8080 --restart=unless-stopped \
  -v /tmp/webapps:/usr/local/tomcat/webapps \
  -v /tmp/conf:/usr/local/tomcat/conf \
  -v /tmp/logs:/usr/local/tomcat/logs \
  -v tomcat_data \
  tomcat:jre9-slim

### Hyperledger Composer
docker volume create hyperledger_data
docker run -d -v hyperledger_data -p 9090:8080 --restart=unless-stopped hyperledger/composer-playground