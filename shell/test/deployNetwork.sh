#!/usr/bin/env bash

scp -i "~/Documents/amazon/blckkey.pem" malevich-network/target/malevich-network.bna ec2-user@ec2-54-172-12-7.compute-1.amazonaws.com:~/conf/composer