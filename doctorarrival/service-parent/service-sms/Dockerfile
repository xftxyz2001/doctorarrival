# Dockerfile for service-sms
FROM xftxyz2001/jdk21:latest

WORKDIR /app

COPY ./service-sms-0.0.1-SNAPSHOT.jar /app/service-sms.jar

ENTRYPOINT ["java", "-jar", "/app/service-sms.jar"]