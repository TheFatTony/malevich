#!/usr/bin/env bash

./downloadFabric.sh
./startFabric.sh
./createPeerAdminCard.sh -h localhost

composer network install --card PeerAdmin@hlfv1 --archiveFile ../malevich-network/target/malevich-network.bna
composer network start --networkName malevich-network --networkVersion 0.0.1 --networkAdmin admin --networkAdminEnrollSecret adminpw --card PeerAdmin@hlfv1 --file networkadmin.card
composer card import --file networkadmin.card


docker run -d \
-e COMPOSER_CARD=admin@malevich-network \
-e COMPOSER_NAMESPACES="never" \
-e COMPOSER_AUTHENTICATION=false \
-e COMPOSER_MULTIUSER=false \
-v ~/.composer:/home/composer/.composer \
--network composer_default \
--name rest -p 3000:3000 \
hyperledger/composer-rest-server:0.19.18

#docker run -d -p 8080:8080 \
#-e COMPOSER_CARD=admin@malevich-network \
#-v ~/.composer:/home/composer/.composer \
#--network composer_default \
#hyperledger/composer-playground:0.19.18