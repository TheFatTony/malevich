#!/usr/bin/env bash

export LC_ALL=en_US.UTF-8


export FABRIC_VERSION=hlfv11

# https://hyperledger.github.io/composer/v0.19/installing/installing-prereqs

npm install -g composer-cli@0.19
npm install -g composer-rest-server@0.19
npm install -g generator-hyperledger-composer@0.19
npm install -g yo
npm install -g composer-playground@0.19



mkdir ~/fabric-dev-servers && cd ~/fabric-dev-servers
curl -O https://raw.githubusercontent.com/hyperledger/composer-tools/master/packages/fabric-dev-servers/fabric-dev-servers.tar.gz
tar -xvf fabric-dev-servers.tar.gz

cd ~/fabric-dev-servers
export FABRIC_VERSION=hlfv11
./downloadFabric.sh

cd ~/fabric-dev-servers
export FABRIC_VERSION=hlfv11
./startFabric.sh
./createPeerAdminCard.sh

composer network install --card PeerAdmin@hlfv1 --archiveFile /tmp/malevich-network.bna
composer network start --networkName malevich-network --networkVersion 0.0.1 --networkAdmin admin --networkAdminEnrollSecret adminpw --card PeerAdmin@hlfv1 --file networkadmin.card
composer card import --file networkadmin.card


docker run -d \
-e COMPOSER_CARD=admin@malevich-network \
-e COMPOSER_NAMESPACES="never" \
-e COMPOSER_AUTHENTICATION=true \
-e COMPOSER_MULTIUSER=false \
-v ~/.composer:/home/composer/.composer \
--network composer_default \
--name rest -p 3000:3000 \
hyperledger/composer-rest-server:0.19.5

docker cp ~/.composer/cards/admin@malevich-network