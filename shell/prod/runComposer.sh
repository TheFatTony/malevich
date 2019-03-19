#!/usr/bin/env bash

docker rm -f composer-inception

docker volume prune -f

docker volume create composer_data

docker run --name composer-inception --privileged -d \
--env="BN_NAME=malevich-network" \
-v ~/conf/composer/malevich-network.bna:/home/dockremap/malevich-network.bna \
-v composer_data \
-p 3000:3000 -p 9090:9090 --restart=unless-stopped composer-inception:0.0