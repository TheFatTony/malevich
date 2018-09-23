#!/usr/bin/env bash

scp -i "~/Documents/amazon/nvirginiadev.pem" malevich-server/target/malevich-srv.war ec2-user@ec2-34-238-136-233.compute-1.amazonaws.com:/tmp/webapps