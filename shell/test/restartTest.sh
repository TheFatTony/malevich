#!/usr/bin/env bash

export LC_ALL=en_US.UTF-8
ssh -i "~/Documents/amazon/blckkey.pem" ec2-user@ec2-54-172-12-7.compute-1.amazonaws.com "docker restart mysql"
ssh -i "~/Documents/amazon/blckkey.pem" ec2-user@ec2-54-172-12-7.compute-1.amazonaws.com "docker restart tomcat"
