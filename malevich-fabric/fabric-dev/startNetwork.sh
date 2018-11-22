#!/usr/bin/env bash

CONTAINER_ALREADY_STARTED="CONTAINER_ALREADY_STARTED_PLACEHOLDER"
if [ ! -e $CONTAINER_ALREADY_STARTED ]; then
    touch $CONTAINER_ALREADY_STARTED
    echo "-- First container startup --"
    ./downloadFabric.sh
    ./startFabric.sh
    ./createPeerAdminCard.sh
    composer network install --card PeerAdmin@hlfv1 --archiveFile ../malevich-network.bna
    composer network start --networkName malevich-network --networkVersion 0.0.1 --networkAdmin admin --networkAdminEnrollSecret adminpw --card PeerAdmin@hlfv1 --file networkadmin.card
    composer card import --file networkadmin.card
    setsid sh -c 'composer-rest-server -c admin@malevich-network -n "never" & composer-playground --port 9090'
else
    echo "-- Not first container startup --"
    setsid sh -c 'composer-rest-server -c admin@malevich-network -n "never" & composer-playground --port 9090'
fi