# Dockerfile for service-common
FROM xftxyz2001/jdk21:latest

WORKDIR /app

COPY ./target/service-common-0.0.1-SNAPSHOT.jar /app/service-common.jar

ENTRYPOINT ["java", "-jar", "/app/service-common.jar"]

EXPOSE 8210