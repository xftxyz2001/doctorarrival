# Dockerfile for service-oss
FROM xftxyz2001/jdk21:latest

WORKDIR /app

COPY ./service-oss-0.0.1-SNAPSHOT.jar /app/service-oss.jar

ENTRYPOINT ["java", "-jar", "/app/service-oss.jar"]

EXPOSE 8260