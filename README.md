# Malevich

## Profiles explained

dev - local developers enviroment


## Build commands

**web-build-test:** 

mvn resources:resources clean install -P test

**srv-build-test:** 

mvn resources:resources clean install -P test

**liquibase:update-test:** 

mvn resources:resources liquibase:dropAll liquibase:updateSQL -P test

## Tests

**VM arguments:**

-Dspring.profiles.active=dev


## Docker Environment

**MySQL:** 
docker run -d -p 3306:3306 --name mysql --restart=unless-stopped \
--env="MYSQL_ROOT_PASSWORD=Orion123" mysql:5.7.23

**Hyperledger**
docker run --name malevich-composer --privileged -d \
-p 3000:3000 -p 8080:8080 --restart=unless-stopped malevich/composer:0.0
