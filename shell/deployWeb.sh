#!/usr/bin/env bash

scp -i "~/Documents/amazon/blckkey.pem" malevich-web/target/malevich-web.war ubuntu@ec2-35-153-69-63.compute-1.amazonaws.com:/tmp/conf/tomcat/webapps