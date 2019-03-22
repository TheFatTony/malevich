#!/usr/bin/env bash

scp -i "~/Documents/amazon/blckkey.pem" malevich-landing/target/malevich-landing.war ec2-user@ec2-52-91-94-37.compute-1.amazonaws.com:/tmp/conf/tomcat/webapps/ROOT.war