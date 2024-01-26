# Dockerfile for service-order
FROM xftxyz2001/jdk21:latest

WORKDIR /app

COPY ./target/service-order-0.0.1-SNAPSHOT.jar /app/service-order.jar

ENTRYPOINT ["java", "-jar", "/app/service-order.jar"]

EXPOSE 8270