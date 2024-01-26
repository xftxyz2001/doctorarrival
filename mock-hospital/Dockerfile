# Dockerfile for mock
FROM container-registry.oracle.com/java/jdk-no-fee-term:21.0.1

WORKDIR /app

COPY ./target/mock-hospital-0.0.1-SNAPSHOT.jar /app/mock-hospital.jar
COPY ./8999.key /app/8999.key
COPY ./hospital.json /app/hospital.json
COPY ./departments.json /app/departments.json
COPY ./schedules.json /app/schedules.json

ENTRYPOINT ["java", "-jar", "/app/mock-hospital.jar"]

EXPOSE 8999