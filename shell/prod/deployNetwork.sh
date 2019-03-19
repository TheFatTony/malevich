#!/usr/bin/env bash

scp -i "~/Documents/amazon/blckkey.pem" malevich-network/target/malevich-network.bna ec2-user@ec2-35-173-181-233.compute-1.amazonaws.com:~/conf/composer