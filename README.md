To run project environment: 
docker volume create portainer_data
docker run -d -p 9000:9000 -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data --restart=unless-stopped portainer/portainer

docker volume create mysql_data
docker run -d -p 3306:3306 -v mysql_data --restart=unless-stopped --env="MYSQL_ROOT_PASSWORD=Orion123" mysql:5.7.23

docker volume create tomcat_data
docker run -d -p 8080:8080 --restart=unless-stopped \
  -v /tmp/webapps:/usr/local/tomcat/webapps \
  -v /tmp/conf:/usr/local/tomcat/conf \
  -v /tmp/logs:/usr/local/tomcat/logs \
  -v tomcat_data \
  tomcat:jre9-slim

docker volume create filemanager_data
docker run -d -p 9090:80 -v filemanager_data --restart=unless-stopped filebrowser/filebrowser:latest

sudo mkdir -p /var/lib/openproject/pgdata
sudo mkdir -p /var/lib/openproject/logs
sudo mkdir -p /var/lib/openproject/static
docker run -d -p 8080:80 -e SECRET_KEY_BASE=secret --restart=unless-stopped \
  -v /var/lib/openproject/pgdata:/var/lib/postgresql/9.6/main \
  -v /var/lib/openproject/logs:/var/log/supervisor \
  -v /var/lib/openproject/static:/var/db/openproject \
  openproject/community:7


docker volume create hyperledger_data
docker run -d -v hyperledger_data -p 9090:8080 --restart=unless-stopped hyperledger/composer-playground