#!/usr/bin/env bash

scp -i "~/Documents/amazon/blckkey.pem" malevich-server/target/malevich-srv.war ec2-user@ec2-35-173-181-233.compute-1.amazonaws.com:~/conf/tomcat/webapps