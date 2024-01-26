# Dockerfile for service-task
FROM xftxyz2001/jdk21:latest

WORKDIR /app

COPY ./target/service-task-0.0.1-SNAPSHOT.jar /app/service-task.jar

ENTRYPOINT ["java", "-jar", "/app/service-task.jar"]

EXPOSE 8280