# “医来”——基于分布式微服务的预约挂号统一平台

## 运行

### 步骤
```bash
# 打包（jdk21、nodejs20
sh build.sh
# 初始化（docker24
sh init.sh
# 构建（docker-compose1.29
docker-compose build
# 运行
docker-compose up -d
```

### 一些配置项（`~/appconfig/xxx/application.yml`）
`service-sms`
```yml
aliyun:
  sms:
    endpoint: <<your endpoint>>
    access-key-id: <<your access-key-id>>
    access-key-secret: <<your access-key-secret>>
    region: <<your region>>
    sign-name: <<your sign-name>>
    template-code-for-verification-code: <<your template-code-for-verification-code>>
    template-code-for-appointment-reminder: <<your template-code-for-appointment-reminder>>
```

`service-oss`
```yml
aliyun:
  oss:
    endpoint: <<your endpoint>>
    access-key-id: <<your access-key-id>>
    access-key-secret: <<your access-key-secret>>
    bucket-name: <<your bucket-name>>
```

`service-order`
```yml
pay:
  ali:
    server-url: <<支付宝网关地址>>
    app-id: <<支付宝应用ID>>
    private-key: <<应用私钥>>
    alipay-public-key: <<支付宝公钥>>
    site-origin: <<站点域名>>
```


## Mock

### 步骤
```bash
cd mock-hospital
# 打包（jdk21
sh build.sh
# 初始化（docker24
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
