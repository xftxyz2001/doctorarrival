# “医来”——基于分布式微服务的预约挂号统一平台

## 运行
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

## 一些配置项
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
