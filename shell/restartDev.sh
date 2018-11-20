#!/usr/bin/env bash

export LC_ALL=en_US.UTF-8
ssh -i "~/Documents/amazon/blckkey.pem" ubuntu@ec2-35-153-69-63.compute-1.amazonaws.com "sudo docker restart mysql"
ssh -i "~/Documents/amazon/blckkey.pem" ubuntu@ec2-35-153-69-63.compute-1.amazonaws.com "sudo docker restart tomcat"
