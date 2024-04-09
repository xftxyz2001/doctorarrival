#!/bin/sh
# nacos
docker run -p 8848:8848 --name nacos -e MODE=standalone -d nacos/nacos-server:v2.3.0
mkdir -p ~/nacos2.3.0
docker cp nacos:/home/nacos/conf ~/nacos2.3.0
docker cp nacos:/home/nacos/logs ~/nacos2.3.0
docker rm -f nacos

# redis
mkdir -p ~/redis7.2.3
touch ~/redis7.2.3/redis.conf

# ---
# service-user
mkdir -p ~/appconfig/service-user
cat << EOF > ~/appconfig/service-user/application.yml
wx:
  open:
    app-id: 
    app-secret: 
    redirect-uri: 
    site-origin: 
EOF

# service-sms
mkdir -p ~/appconfig/service-sms
cat << EOF > ~/appconfig/service-sms/application.yml
aliyun:
  sms:
    endpoint: 
    access-key-id: 
    access-key-secret: 
    region: 
    sign-name: 
    template-code-for-verification-code: 
    template-code-for-appointment-reminder: 
EOF

# service-oss
mkdir -p ~/appconfig/service-oss
cat << EOF > ~/appconfig/service-oss/application.yml
aliyun:
  oss:
    endpoint: 
    access-key-id: 
    access-key-secret: 
    bucket-name: 
EOF

# service-order
mkdir -p ~/appconfig/service-order
cat << EOF > ~/appconfig/service-order/application.yml
pay:
  ali:
    server-url: 
    app-id: 
    private-key: 
    alipay-public-key: 
    site-origin: 
EOF
