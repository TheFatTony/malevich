#!/usr/bin/env bash

scp -ri "~/Documents/amazon/blckkey.pem" ./conf/* ec2-user@ec2-54-172-12-7.compute-1.amazonaws.com:/tmp/conf