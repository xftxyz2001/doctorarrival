# Dockerfile for service-task
FROM container-registry.oracle.com/java/jdk-no-fee-term:21.0.1

WORKDIR /app

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY ./target/service-task-0.0.1-SNAPSHOT.jar /app/service-task.jar

ENTRYPOINT ["java", "-jar", "/app/service-task.jar"]

EXPOSE 8280