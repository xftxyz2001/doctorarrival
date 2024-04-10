#!/bin/sh
# 0 清理
docker rm -f $(docker ps -aq)

# 1 构建
## 后端
cd doctorarrival
chmod +x mvnw
./mvnw clean install -DskipTests

## 前端（门户）
cd ../doctorarrival-site
npm install
npm run build

## 前端（医生后台管理）
cd ../doctorarrival-admin
npm install
npm run build

## mock
cd ../mock-hospital
chmod +x mvnw
./mvnw clean package -DskipTests
docker rmi -f xftxyz/mock:0.0.1
docker build -t xftxyz/mock:0.0.1 .

## 构建docker镜像
cd ..
docker rmi -f xftxyz/doctorarrival-gateway:0.0.1
docker rmi -f xftxyz/doctorarrival-common:0.0.1
docker rmi -f xftxyz/doctorarrival-hospital:0.0.1
docker rmi -f xftxyz/doctorarrival-user:0.0.1
docker rmi -f xftxyz/doctorarrival-sms:0.0.1
docker rmi -f xftxyz/doctorarrival-oss:0.0.1
docker rmi -f xftxyz/doctorarrival-order:0.0.1
docker rmi -f xftxyz/doctorarrival-task:0.0.1
docker rmi -f xftxyz/doctorarrival-statistics:0.0.1
docker rmi -f xftxyz/doctorarrival-site:0.0.1
docker rmi -f xftxyz/doctorarrival-admin:0.0.1

docker-compose build

# 2 初始化
## nacos
if [ ! -d "~/nacos2.3.0" ]; then
    docker run -p 8848:8848 --name nacos -e MODE=standalone -d nacos/nacos-server:v2.3.0
    mkdir -p ~/nacos2.3.0
    docker cp nacos:/home/nacos/conf ~/nacos2.3.0
    docker cp nacos:/home/nacos/logs ~/nacos2.3.0
    docker rm -f nacos
fi
echo "nacos2.3.0已经初始化完成！"

## redis
if [ ! -d "~/redis7.2.3" ]; then
    mkdir -p ~/redis7.2.3
    touch ~/redis7.2.3/redis.conf
fi
echo "redis7.2.3已经初始化完成！"

service_user_exists=[ -d "~/appconfig/service-user" ]
service_sms_exists=[ -d "~/appconfig/service-sms" ]
service_oss_exists=[ -d "~/appconfig/service-oss" ]
service_order_exists=[ -d "~/appconfig/service-order" ]

if ! $service_user_exists && ! $service_order_exists; then
    read -p "请输入项目部署的域名（eg: doctorarrival.com/localhost/127.0.0.1）: " domain
fi

## service-user
if ! $service_user_exists; then
    mkdir -p ~/appconfig/service-user
    echo "下面是微信相关配置，参考: "
    echo "https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index"

    read -p "请输入微信公众号的AppID（直接回车则使用默认）: " appID

    if [ -z "$appID" ]; then
        appID="wxed9954c01bb89b47"
        appsecret="a7482517235173ddb4083788de60b90e"
        redirect_uri="http://localhost:8160/api/ucenter/wx/callback"
    else
        read -p "请输入微信公众号的AppSecret: " appsecret
        redirect-uri="http://$domain/api/user/wx/callback"
    fi

    cat <<EOF >~/appconfig/service-user/application.yml
wx:
  open:
    app-id: $appID
    app-secret: $appsecret
    token: xftxyz
    redirect-uri: $redirect_uri
    site-origin: http://$domain:3000
EOF

    if [ "$appID" = "wxed9954c01bb89b47" ]; then
        echo "接口转发: "
        echo "listen       8160;"
        echo "server_name  localhost;"
        echo "location /api/ucenter/wx/callback {"
        echo "    proxy_pass http://$domain/api/user/wx/callback;"
        echo "}"
    else
        echo "微信公众号接口配置: "
        echo "URL: http://$domain/api/user/wx/verify"
        echo "Token: xftxyz"
        echo "JS接口安全域名: http://$domain:3000"
    fi
fi

if ! $service_sms_exists && ! $service_oss_exists; then
    echo "下面是阿里云相关配置，参考: "
    echo "https://ram.console.aliyun.com/manage/ak"
    read -p "请输入阿里云的AccessKey ID: " accesskeyid
    read -p "请输入阿里云的AccessKey Secret: " accesskeysecret
fi

## service-sms
if ! $service_sms_exists; then
    mkdir -p ~/appconfig/service-sms
    echo "下面是阿里云短信相关配置，参考: "
    echo "https://dysms.console.aliyun.com/quickstart"

    read -p "请输入阿里云短信的验证码模版代码: " verificationcode

    cat <<EOF >~/appconfig/service-sms/application.yml
aliyun:
  sms:
    endpoint: dysmsapi.aliyuncs.com
    access-key-id: $accesskeyid
    access-key-secret: $accesskeysecret
    region: cn-hangzhou
    sign-name: 阿里云短信测试
    template-code-for-verification-code: $verificationcode
    template-code-for-appointment-reminder: unknown
EOF
fi

## service-oss
if ! $service_oss_exists; then
    mkdir -p ~/appconfig/service-oss

    cat <<EOF >~/appconfig/service-oss/application.yml
aliyun:
  oss:
    endpoint: oss-cn-hangzhou.aliyuncs.com
    access-key-id: $accesskeyid
    access-key-secret: $accesskeysecret
    bucket-name: doctorarrival
EOF
fi

## service-order
if ! $service_order_exists; then
    mkdir -p ~/appconfig/service-order
    echo "下面是支付宝沙箱相关配置，参考: "
    echo "https://open.alipay.com/develop/sandbox/app"

    read -p "请输入支付宝沙箱的AppID: " appid
    read -p "请输入支付宝沙箱的应用私钥: " privatekey
    read -p "请输入支付宝沙箱的支付宝公钥: " alipaypublickey

    cat <<EOF >~/appconfig/service-order/application.yml
pay:
  ali:
    server-url: https://openapi-sandbox.dl.alipaydev.com/gateway.do
    app-id: $appid
    private-key: $privatekey
    alipay-public-key: $alipaypublickey
    site-origin: http://$domain:3000
EOF
fi

echo "微服务已配置完成！"

# 3 启动
docker-compose up -d

docker run \
--name mock \
--restart unless-stopped \
-p 8999:8999 \
--network doctorarrival-network \
--privileged=true \
-v ~/appconfig/mock:/app/config \
-d xftxyz/mock:0.0.1
