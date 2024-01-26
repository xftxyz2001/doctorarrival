# Dockerfile for service-statistics
FROM container-registry.oracle.com/java/jdk-no-fee-term:21.0.1

WORKDIR /app

COPY ./target/service-statistics-0.0.1-SNAPSHOT.jar /app/service-statistics.jar

ENTRYPOINT ["java", "-jar", "/app/service-statistics.jar"]

EXPOSE 8290