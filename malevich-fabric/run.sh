#!/usr/bin/env bash

docker run --name malevich-composer --privileged -p 3000:3000 -p 8080:8080 -d malevich/composer:0.0
#docker exec malevich-composer /bin/bash ./downloadFabric.sh
#docker exec malevich-composer /bin/bash ./startFabric.sh
#docker exec malevich-composer /bin/bash ./createPeerAdminCard.sh
#docker cp ../malevich-network/target/malevich-network.bna malevich-composer:/home/dockremap
#docker exec malevich-composer /bin/bash ./startNetwork.sh
#docker exec malevich-composer /bin/bash "setsid composer-playground"
#docker exec malevich-composer /bin/bash "setsid composer-rest-server -c admin@malevich-network -n \"never\" &> /dev/null"