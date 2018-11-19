#!/usr/bin/env bash

export LC_ALL=en_US.UTF-8


export FABRIC_VERSION=hlfv11


setsid composer-playground &> /dev/null

setsid composer-rest-server -c admin@malevich-network -n "never" &> /dev/null
