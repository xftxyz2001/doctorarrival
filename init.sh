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
read -p "请输入项目部署的域名（eg: doctorarrival.com/localhost/127.0.0.1）: " domain

# service-user
mkdir -p ~/appconfig/service-user
echo "下面是微信相关配置，参考: "
echo "https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index"

read -p "请输入微信公众号的AppID: " appID
read -p "请输入微信公众号的AppSecret: " appsecret

cat << EOF > ~/appconfig/service-user/application.yml
wx:
  open:
    app-id: $appID
    app-secret: $appsecret
    token: xftxyz
    redirect-uri: http://$domain/api/user/wx/callback
    site-origin: http://$domain:3000
EOF

# service-sms
mkdir -p ~/appconfig/service-sms
echo "下面是阿里云相关配置，参考: "
echo "https://ram.console.aliyun.com/manage/ak"
echo "https://dysms.console.aliyun.com/quickstart"

read -p "请输入阿里云的AccessKey ID: " accesskeyid
read -p "请输入阿里云的AccessKey Secret: " accesskeysecret
read -p "请输入阿里云短信的验证码模版代码: " verificationcode

cat << EOF > ~/appconfig/service-sms/application.yml
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

# service-oss
mkdir -p ~/appconfig/service-oss

cat << EOF > ~/appconfig/service-oss/application.yml
aliyun:
  oss:
    endpoint: oss-cn-hangzhou.aliyuncs.com
    access-key-id: $accesskeyid
    access-key-secret: $accesskeysecret
    bucket-name: doctorarrival
EOF

# service-order
mkdir -p ~/appconfig/service-order
echo "下面是支付宝沙箱相关配置，参考: "
echo "https://open.alipay.com/develop/sandbox/app"

read -p "请输入支付宝沙箱的AppID: " appid
read -p "请输入支付宝沙箱的应用私钥: " privatekey
read -p "请输入支付宝沙箱的支付宝公钥: " alipaypublickey

cat << EOF > ~/appconfig/service-order/application.yml
pay:
  ali:
    server-url: https://openapi-sandbox.dl.alipaydev.com/gateway.do
    app-id: $appid
    private-key: $privatekey
    alipay-public-key: $alipaypublickey
    site-origin: http://$domain:3000
EOF

echo "初始化完成！"
