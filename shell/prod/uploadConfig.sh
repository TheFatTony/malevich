#!/usr/bin/env bash

scp -ri "~/Documents/amazon/blckkey.pem" ./conf/* ec2-user@ec2-35-173-181-233.compute-1.amazonaws.com:~/conf