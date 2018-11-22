FROM docker:dind

RUN apk update && \
    apk add && \
    apk add \
    make \
    gcc \
    g++ \
    curl \
    nodejs \
    npm \
    python \
    python-dev \
    py-pip \
    bash \
    git \
    openssh

RUN npm i npm@latest -g && pip install --upgrade pip

RUN pip install 'docker-compose==1.8.0'

RUN npm install --unsafe-perm -g \
    composer-cli@0.19 \
    composer-rest-server@0.19 \
    composer-playground@0.19

COPY fabric-dev /home/dockremap/fabric-dev

COPY malevich-network.bna /home/dockremap

WORKDIR /home/dockremap/fabric-dev

EXPOSE 3000 8080

# Install the magic wrapper.
ADD ./wrapdocker /usr/local/bin/wrapdocker
RUN chmod +x /usr/local/bin/wrapdocker

CMD ["wrapdocker", "./startNetwork.sh"]