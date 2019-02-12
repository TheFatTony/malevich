#!/usr/bin/env bash

scp -ri "~/Documents/amazon/blckkey.pem" conf/* ec2-user@ec2-52-91-94-37.compute-1.amazonaws.com:/tmp/conf