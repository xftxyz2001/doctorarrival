# “医来”——基于分布式微服务的预约挂号统一平台

## 运行
```bash
sh run_allinone.sh
```

### 环境
- JDK 21+
- Node.js 20+
- Docker 25+
- Docker Compose 1.29+


### Mock配置项（可选）
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
