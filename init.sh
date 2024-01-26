# nacos
docker run -p 8848:8848 --name nacos -e MODE=standalone -d nacos/nacos-server:v2.3.0
mkdir -p ~/nacos2.3.0
docker cp nacos:/home/nacos/conf ~/nacos2.3.0
docker cp nacos:/home/nacos/logs ~/nacos2.3.0
docker rm -f nacos

# mysql
docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:8.2.0
docker cp mysql:/etc/mysql ~/mysql8.2.0
docker rm -f mysql

# mongodb
mkdir -p ~/mongo6.0.12/{data,conf,backup}

# redis
mkdir -p ~/redis7.2.3
touch ~/redis7.2.3/redis.conf

# rabbitmq
mkdir -p ~/rabbitmq3.12
