# “医来”——基于分布式微服务的预约挂号统一平台

## 运行

### 步骤
```bash
# 打包（jdk21、nodejs20
sh build.sh
# 初始化（仅首次，docker24
sh init.sh
# 构建（docker-compose1.29
docker-compose build
# 运行
docker-compose up -d
```

### 一些配置项（`~/appconfig/xxx/application.yml`）
`service-user`
```yml
# https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index
wx:
  open:
    app-id: <<appID>>
    app-secret: <<appsecret>>
    redirect-uri: <<网关（后端）域名+/api/user/wx/callback>>
    site-origin: <<站点（前端）域名>>
```

`service-sms`
```yml
# https://ram.console.aliyun.com/manage/ak
aliyun:
  sms:
    endpoint: dysmsapi.aliyuncs.com
    access-key-id: <<AccessKey ID>>
    access-key-secret: <<AccessKey Secret>>
    region: cn-hangzhou
    sign-name: <<短信签名名称>>
    template-code-for-verification-code: <<验证码的模版代码>>
    template-code-for-appointment-reminder: <<就诊通知的模版代码>>
```

`service-oss`
```yml
# https://oss.console.aliyun.com/bucket
aliyun:
  oss:
    endpoint: oss-cn-hangzhou.aliyuncs.com
    access-key-id: <<AccessKey ID>>
    access-key-secret: <<AccessKey Secret>>
    bucket-name: <<Bucket 名称>>
```

`service-order`
```yml
# https://open.alipay.com/develop/sandbox/app
pay:
  ali:
    server-url: https://openapi-sandbox.dl.alipaydev.com/gateway.do
    app-id: <<APPID>>
    private-key: <<应用私钥>>
    alipay-public-key: <<支付宝公钥>>
    site-origin: <<站点（前端）域名>>
```


## Mock

### 步骤
```bash
cd mock-hospital
# 打包（jdk21
sh build.sh
# 初始化（仅首次，docker24
sh init.sh
# 运行
sh run.sh
```

### 一些配置项（可选）
- `~/appconfig/mock/application.yml`
  ```yml
  doctorarrival:
    serverUrl: http://service-gateway/api/hospital/side
    hospitalCode: 8999
    privateKeyLocation: config/8999.key
  ```
- `/appconfig/mock/8999.key`
- `/appconfig/mock/departments.json`
- `/appconfig/mock/hospital.json`
- `/appconfig/mock/schedules.json`


## 开发

### 环境
- JDK 21.0.1
- Node.js 20.10.0
- Nacos 2.3.0
- MySQL 8.2.0
- MongoDB 6.0.12
- Redis 7.2.3
- RabbitMQ 3.12

### hosts
```
127.0.0.1 nacos
127.0.0.1 mysql
127.0.0.1 mongodb
127.0.0.1 redis
127.0.0.1 rabbitmq
127.0.0.1 service-gateway
127.0.0.1 mock
```
