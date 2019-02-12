#!/usr/bin/env bash

scp -i "~/Documents/amazon/blckkey.pem" malevich-web/target/malevich-web.war ec2-user@ec2-54-172-12-7.compute-1.amazonaws.com:/tmp/conf/tomcat/webapps