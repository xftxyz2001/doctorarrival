# Dockerfile for service-gateway
FROM xftxyz2001/jdk21:latest

WORKDIR /app

COPY ./target/service-gateway-0.0.1-SNAPSHOT.jar /app/service-gateway.jar

ENTRYPOINT ["java", "-jar", "/app/service-gateway.jar"]

EXPOSE 80