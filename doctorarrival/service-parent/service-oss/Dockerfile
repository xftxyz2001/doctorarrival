# Dockerfile for service-oss
FROM container-registry.oracle.com/java/jdk-no-fee-term:21.0.1

WORKDIR /app

COPY ./target/service-oss-0.0.1-SNAPSHOT.jar /app/service-oss.jar

ENTRYPOINT ["java", "-jar", "/app/service-oss.jar"]

EXPOSE 8260