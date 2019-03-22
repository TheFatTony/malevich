#!/usr/bin/env bash

docker volume create tomcat_data

docker run -d -p 8080:8080 --restart=unless-stopped \
-v ~/conf/tomcat/webapps:/usr/local/tomcat/webapps \
-v ~/conf/tomcat/conf:/usr/local/tomcat/conf \
-v ~/conf/tomcat/logs:/usr/local/tomcat/logs \
-v tomcat_data --name tomcat tomcat:jre9-slim