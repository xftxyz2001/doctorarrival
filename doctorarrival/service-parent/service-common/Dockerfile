# Dockerfile for service-common
FROM container-registry.oracle.com/java/jdk-no-fee-term:21.0.1

WORKDIR /app

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


COPY ./target/service-common-0.0.1-SNAPSHOT.jar /app/service-common.jar

ENTRYPOINT ["java", "-jar", "/app/service-common.jar"]

EXPOSE 8210