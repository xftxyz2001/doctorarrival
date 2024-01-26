# Dockerfile for service-gateway
FROM container-registry.oracle.com/java/jdk-no-fee-term:21.0.1

WORKDIR /app

COPY ./target/service-gateway-0.0.1-SNAPSHOT.jar /app/service-gateway.jar

ENTRYPOINT ["java", "-jar", "/app/service-gateway.jar"]

EXPOSE 80