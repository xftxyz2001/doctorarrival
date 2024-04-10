# Dockerfile for mock
FROM container-registry.oracle.com/java/jdk-no-fee-term:21.0.1

WORKDIR /app

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone


COPY ./target/mock-hospital-0.0.1-SNAPSHOT.jar /app/mock-hospital.jar
# COPY config/8999.key /app/config/8999.key
# COPY config/hospital.json /app/config/hospital.json
# COPY config/departments.json /app/config/departments.json
# COPY config/schedules.json /app/config/schedules.json

ENTRYPOINT ["java", "-jar", "/app/mock-hospital.jar"]

EXPOSE 8999