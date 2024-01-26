# Dockerfile for service-hospital
FROM xftxyz2001/jdk21:latest

WORKDIR /app

COPY ./target/service-hospital-0.0.1-SNAPSHOT.jar /app/service-hospital.jar

ENTRYPOINT ["java", "-jar", "/app/service-hospital.jar"]

EXPOSE 8220