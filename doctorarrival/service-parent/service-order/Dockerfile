# Dockerfile for service-order
FROM container-registry.oracle.com/java/jdk-no-fee-term:21.0.1

WORKDIR /app

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


COPY ./target/service-order-0.0.1-SNAPSHOT.jar /app/service-order.jar

ENTRYPOINT ["java", "-jar", "/app/service-order.jar"]

EXPOSE 8270