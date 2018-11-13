#!/usr/bin/env bash

export LC_ALL=en_US.UTF-8
export FABRIC_VERSION=hlfv11
ssh -i "~/Documents/amazon/nvirginiadev.pem" ec2-user@ec2-34-238-136-233.compute-1.amazonaws.com "setsid composer-playground &> /dev/null"