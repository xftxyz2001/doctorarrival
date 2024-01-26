# Dockerfile for service-common
FROM container-registry.oracle.com/java/jdk-no-fee-term:21.0.1

WORKDIR /app

COPY ./target/service-common-0.0.1-SNAPSHOT.jar /app/service-common.jar

ENTRYPOINT ["java", "-jar", "/app/service-common.jar"]

EXPOSE 8210