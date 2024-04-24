#!/bin/sh
# 0 清理
docker rm -f $(docker ps -aq)

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

docker rmi -f xftxyz/mock:0.0.1

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
docker build -t xftxyz/mock:0.0.1 .

## 构建docker镜像
cd ..
docker-compose build

# 2 初始化
if [ ! -d ./config/service-user ] && [ ! -d ./config/service-order ]; then
    domain=$(curl -s ip.sb)
    echo "项目将部署在: $domain"
fi

## service-user
if [ ! -d ./config/service-user ]; then
    mkdir -p ./config/service-user
    echo "下面是微信相关配置，参考: "
    echo "https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index"

    read -p "请输入微信公众号的AppID（直接回车则使用默认）: " appID

    if [ -z "$appID" ]; then
        appID="wxed9954c01bb89b47"
        appsecret="a7482517235173ddb4083788de60b90e"
        redirect_uri="http://localhost:8160/api/ucenter/wx/callback"
    else
        read -p "请输入微信公众号的AppSecret: " appsecret
        redirect_uri="http://$domain/api/user/wx/callback"
    fi

    cat <<EOF >./config/service-user/application.yml
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

if [ ! -d ./config/service-sms ] && [ ! -d ./config/service-oss ]; then
    echo "下面是阿里云相关配置，参考: "
    echo "https://ram.console.aliyun.com/manage/ak"
    read -p "请输入阿里云的AccessKey ID: " accesskeyid
    read -p "请输入阿里云的AccessKey Secret: " accesskeysecret
fi

## service-sms
if [ ! -d ./config/service-sms ]; then
    mkdir -p ./config/service-sms
    echo "下面是阿里云短信相关配置，参考: "
    echo "https://dysms.console.aliyun.com/quickstart"

    read -p "请输入阿里云短信的验证码模版代码: " verificationcode

    cat <<EOF >./config/service-sms/application.yml
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
if [ ! -d ./config/service-oss ]; then
    mkdir -p ./config/service-oss

    cat <<EOF >./config/service-oss/application.yml
aliyun:
  oss:
    endpoint: oss-cn-hangzhou.aliyuncs.com
    access-key-id: $accesskeyid
    access-key-secret: $accesskeysecret
    bucket-name: doctorarrival
EOF
fi

## service-order
if [ ! -d ./config/service-order ]; then
    mkdir -p ./config/service-order
    echo "下面是支付宝沙箱相关配置，参考: "
    echo "https://open.alipay.com/develop/sandbox/app"

    read -p "请输入支付宝沙箱的AppID: " appid
    read -p "请输入支付宝沙箱的应用私钥: " privatekey
    read -p "请输入支付宝沙箱的支付宝公钥: " alipaypublickey

    cat <<EOF >./config/service-order/application.yml
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
-v ./mock-hospital/config:/app/config \
-d xftxyz/mock:0.0.1

echo "服务已启动，需放行端口：80（网关）、3000（前台）、3001（后台）。
