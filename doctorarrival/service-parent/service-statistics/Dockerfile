# Dockerfile for service-statistics
FROM xftxyz2001/jdk21:latest

WORKDIR /app

COPY ./service-statistics-0.0.1-SNAPSHOT.jar /app/service-statistics.jar

ENTRYPOINT ["java", "-jar", "/app/service-statistics.jar"]

EXPOSE 8290