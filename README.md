# Malevich

## Git

To avoid EOL issues on Windows

```
git config --global core.autocrlf true
rm .git/index
git reset --hard
```

## Profiles explained

dev - local developers environment


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
-p 3000:3000 -p 9090:9090 --restart=unless-stopped composer-inception:0.0


## URLs
Angular App: http://localhost:4200
Spring Boot: http://localhost:8080

Docker Playground: http://localhost:9090
Docker REST Server: http://localhost:3000/explorer/