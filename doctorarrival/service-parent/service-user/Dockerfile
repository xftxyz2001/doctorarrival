# Dockerfile for service-user
FROM container-registry.oracle.com/java/jdk-no-fee-term:21.0.1

WORKDIR /app

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


COPY ./target/service-user-0.0.1-SNAPSHOT.jar /app/service-user.jar

ENTRYPOINT ["java", "-jar", "/app/service-user.jar"]

EXPOSE 8230