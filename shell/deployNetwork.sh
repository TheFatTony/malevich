#!/usr/bin/env bash

scp -i "~/Documents/amazon/blckkey.pem" malevich-network/target/malevich-network.bna ubuntu@ec2-35-153-69-63.compute-1.amazonaws.com:/tmp/conf/composer