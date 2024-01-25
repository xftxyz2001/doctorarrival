# Dockerfile for service-user
FROM xftxyz2001/jdk21:latest

WORKDIR /app

COPY ./service-user-0.0.1-SNAPSHOT.jar /app/service-user.jar

ENTRYPOINT ["java", "-jar", "/app/service-user.jar"]

EXPOSE 8230