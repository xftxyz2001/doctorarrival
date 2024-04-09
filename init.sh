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
# https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index

# wx:
#   open:
#     app-id: <<appID>>
#     app-secret: <<appsecret>>
#     redirect-uri: http://localhost/api/user/wx/callback
#     site-origin: http://localhost:3000
EOF

# service-sms
mkdir -p ~/appconfig/service-sms
cat << EOF > ~/appconfig/service-sms/application.yml
# https://ram.console.aliyun.com/manage/ak
# https://dysms.console.aliyun.com/quickstart

# aliyun:
#   sms:
#     endpoint: dysmsapi.aliyuncs.com
#     access-key-id: <<AccessKey ID>>
#     access-key-secret: <<AccessKey Secret>>
#     region: cn-hangzhou
#     sign-name: <<短信签名名称>>
#     template-code-for-verification-code: <<验证码的模版代码>>
#     template-code-for-appointment-reminder: <<就诊通知的模版代码>>
EOF

# service-oss
mkdir -p ~/appconfig/service-oss
cat << EOF > ~/appconfig/service-oss/application.yml
# https://ram.console.aliyun.com/manage/ak
# https://oss.console.aliyun.com/bucket

# aliyun:
#   oss:
#     endpoint: oss-cn-hangzhou.aliyuncs.com
#     access-key-id: <<AccessKey ID>>
#     access-key-secret: <<AccessKey Secret>>
#     bucket-name: doctorarrival
EOF

# service-order
mkdir -p ~/appconfig/service-order
cat << EOF > ~/appconfig/service-order/application.yml
# https://open.alipay.com/develop/sandbox/app

# pay:
#   ali:
#     server-url: https://openapi-sandbox.dl.alipaydev.com/gateway.do
#     app-id: <<APPID>>
#     private-key: <<应用私钥>>
#     alipay-public-key: <<支付宝公钥>>
#     site-origin: http://localhost:3000
EOF
